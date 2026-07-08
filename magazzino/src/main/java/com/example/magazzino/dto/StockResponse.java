package com.example.magazzino.dto;

public record StockResponse(

    Long id,

    String prodotto,

    int quantitaDisponibile
){}
