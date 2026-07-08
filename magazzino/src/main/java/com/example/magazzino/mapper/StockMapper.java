package com.example.magazzino.mapper;

import com.example.magazzino.dto.StockResponse;
import com.example.magazzino.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {
    
    public StockResponse toResponse(Stock stock){
        StockResponse response = new StockResponse(

            stock.getId(),

            stock.getProdotto(),

            stock.getQuantitaDisponibile()
        );

        return response;
    }
}
