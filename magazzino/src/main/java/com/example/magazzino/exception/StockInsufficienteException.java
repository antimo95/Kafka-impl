package com.example.magazzino.exception;

public class StockInsufficienteException extends RuntimeException {

    public StockInsufficienteException(String prodotto, int richiesti, int disponibili) {
        super("Stock insufficiente per '" + prodotto + "': richiesti "
                + richiesti + ", disponibili " + disponibili);
    }
}
