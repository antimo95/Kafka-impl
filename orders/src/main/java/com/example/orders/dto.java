package com.example.orders;


record CreateOrderRequest(
    @NotNull UUID customerId, 
    @PositiveOrZero double total
){}

record OrderResponse(
    java.util.UUID id,
    String status
){}