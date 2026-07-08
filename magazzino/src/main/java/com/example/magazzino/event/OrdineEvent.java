package com.example.magazzino.event;

import java.time.Instant;

/**
 * Copia dell'evento lato CONSUMER: stessa forma di quella in 'ordini',
 * ma classe diversa in un package diverso. Quando arriva un messaggio dal
 * topic, il JsonDeserializer legge il JSON e riempie questo record.
 */
public record OrdineEvent(
        String eventType,
        Long ordineId,
        String prodotto,
        Integer quantita,
        String cliente,
        Instant timestamp
) {}
