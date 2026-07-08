package com.example.magazzino.controller;

import com.example.magazzino.dto.StockRequest;
import com.example.magazzino.dto.StockResponse;
import com.example.magazzino.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "Magazzino", description = "Gestione delle scorte")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "Registra o rifornisce lo stock di un prodotto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse registra(@Valid @RequestBody StockRequest request) {
        return stockService.registra(request);
    }

    @Operation(summary = "Elenca lo stock di tutti i prodotti")
    @GetMapping
    public List<StockResponse> findAll() {
        return stockService.findAll();
    }

    @Operation(summary = "Disponibilità di un singolo prodotto")
    @GetMapping("/{prodotto}")
    public StockResponse findByProdotto(@PathVariable String prodotto) {
        return stockService.findByProdotto(prodotto);
    }
}
