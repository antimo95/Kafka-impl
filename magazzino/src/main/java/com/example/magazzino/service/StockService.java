package com.example.magazzino.service;

import com.example.magazzino.dto.StockRequest;
import com.example.magazzino.dto.StockResponse;
import com.example.magazzino.entity.Stock;
import com.example.magazzino.exception.StockNotFoundException;
import com.example.magazzino.mapper.StockMapper;
import com.example.magazzino.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public StockService(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * Registra stock: se il prodotto esiste già, SOMMA la quantità
     * (rifornimento); altrimenti crea un nuovo record.
     */
    public StockResponse registra(StockRequest request) {
        Stock stock = stockRepository.findByProdotto(request.prodotto())
                .orElseGet(() -> {
                    Stock nuovo = new Stock();
                    nuovo.setProdotto(request.prodotto());
                    nuovo.setQuantitaDisponibile(0);
                    return nuovo;
                });

        stock.setQuantitaDisponibile(stock.getQuantitaDisponibile() + request.quantita());
        Stock salvato = stockRepository.save(stock);
        return stockMapper.toResponse(salvato);
    }

    public List<StockResponse> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(stockMapper::toResponse)
                .toList();
    }

    public StockResponse findByProdotto(String prodotto) {
        Stock stock = stockRepository.findByProdotto(prodotto)
                .orElseThrow(() -> new StockNotFoundException(prodotto));
        return stockMapper.toResponse(stock);
    }

    /**
     * Scala la disponibilità di un prodotto.
     *
     * Per ora NON è usato da nessun endpoint REST: lo prepariamo perché
     * sarà il metodo che, all'ultimo step, il consumer Kafka chiamerà
     * quando arriva un evento "OrdineCreato". Se lo stock non basta,
     * per semplicità lo portiamo a 0 e segnaliamo il problema a chi chiama.
     */
    public void decrementa(String prodotto, int quantita) {
        Stock stock = stockRepository.findByProdotto(prodotto)
                .orElseThrow(() -> new StockNotFoundException(prodotto));

        int nuovaQuantita = stock.getQuantitaDisponibile() - quantita;
        if (nuovaQuantita < 0) {
            stock.setQuantitaDisponibile(0);
            stockRepository.save(stock);
            throw new IllegalStateException(
                    "Stock insufficiente per '" + prodotto + "': richiesti "
                            + quantita + ", disponibili " + stock.getQuantitaDisponibile());
        }

        stock.setQuantitaDisponibile(nuovaQuantita);
        stockRepository.save(stock);
    }
}
