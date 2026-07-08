package com.example.magazzino.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockRequest(

    @NotBlank(message = "Il prodotto deve essere inserito")
    String prodotto,

    @NotBlank(message = "La quantità deve essere inserita ")
    @PositiveOrZero(message = "La quantità non può essere negativa")
    Integer quantita
){}
