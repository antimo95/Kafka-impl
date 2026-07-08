package com.example.magazzino.listener;

import com.example.magazzino.event.OrdineEvent;
import com.example.magazzino.exception.StockInsufficienteException;
import com.example.magazzino.exception.StockNotFoundException;
import com.example.magazzino.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Il CONSUMER: il cuore dell'integrazione.
 *
 * Resta in ascolto sul topic 'ordini-eventi'. A ogni evento, scala lo stock
 * del prodotto ordinato chiamando StockService.decrementa(...).
 *
 * Nota: qui NON c'è nessun riferimento a 'ordini'. Il magazzino non sa chi
 * pubblica: reagisce e basta. È il disaccoppiamento.
 */
@Component
public class OrdineEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrdineEventListener.class);

    private final StockService stockService;

    public OrdineEventListener(StockService stockService) {
        this.stockService = stockService;
    }

    // 'topics': il topic Kafka a cui questo metodo si iscrive come consumer.
    // 'groupId': il consumer group. Tutte le istanze dell'app con lo stesso
    // groupId si dividono le partizioni del topic (scaling orizzontale);
    // se in futuro scali il magazzino su più istanze, i messaggi vengono
    // bilanciati tra loro e non duplicati.
    @KafkaListener(topics = "ordini-eventi", groupId = "magazzino")
    public void onOrdineCreato(OrdineEvent evento) {
        // Il payload JSON del messaggio Kafka è già stato deserializzato
        // in un OrdineEvent (vedi la configurazione del JsonDeserializer)
        // prima che questo metodo venga invocato.
        log.info(">>> Ricevuto {} per ordine {}: {} x{} (cliente {})",
                evento.eventType(), evento.ordineId(),
                evento.prodotto(), evento.quantita(), evento.cliente());

        // Le eccezioni vengono gestite QUI dentro, non lasciate propagare:
        // se uscissero dal listener, l'error handler di default di Spring
        // Kafka le tratterebbe come errori transitori e ritenterebbe la
        // consegna dello stesso messaggio all'infinito. Trattandosi invece
        // di errori di business (non di un problema momentaneo), li logghiamo
        // e l'offset del messaggio viene comunque confermato (commit).
        try {
            stockService.decrementa(evento.prodotto(), evento.quantita());
            log.info("    -> Stock aggiornato per '{}'", evento.prodotto());
        } catch (StockNotFoundException e) {
            // Prodotto mai registrato a magazzino: qui, per la demo, logghiamo.
            // In un sistema reale potresti pubblicare un evento "stock mancante".
            log.warn("    -> Prodotto '{}' non presente a magazzino, evento ignorato", evento.prodotto());
        } catch (StockInsufficienteException e) {
            // Stock insufficiente.
            log.warn("    -> {}", e.getMessage());
        }
    }
}
