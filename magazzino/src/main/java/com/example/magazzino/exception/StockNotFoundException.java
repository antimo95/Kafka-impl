package com.example.magazzino.exception;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String prodotto) {
        super("Prodotto non trovato in magazzino: " + prodotto);
    }
}
