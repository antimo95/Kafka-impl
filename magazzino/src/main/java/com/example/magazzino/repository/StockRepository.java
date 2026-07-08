package com.example.magazzino.repository;

import com.example.magazzino.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProdotto(String prodotto);
}