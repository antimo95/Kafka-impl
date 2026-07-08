package com.example.orders; 

import org.springframework.stereotype.Service;
import java.util.UUID; 

@Service
public class OrderService{

    private final OrderRepository repository;


    public OrderService(OrderRepository repository){
        this.repository = repository; 
    }

    public Order createOrder(UUID customerId, double totale){
        Order order = new Order(customerId, totale);
        return repository.save(order);
    }

    public Order getOrder(UUID id){
        return repository.findById(id)
               .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
    }

    public Order ConfirmOrder(UUID id){
        Order order = getOrder(id);
        order.setStatus(OrderStatus.CONFIRMED);
        return repository.save(order);
    }


}