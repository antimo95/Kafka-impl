package com.example.orders; 



@RestController
@RequestMapping("/orders")
public class OrderController{

    private final OrderService service; 
    
    
    public OrderController(Orderservice service){
        this.service = service; 
    }

    @PostMapping
    public Order createOrder(UUID customerId, double totale){
        return service.createOrder(customerId, totale);
    }

    @GetMapping("/{id}")
    public Order getOrder(UUID id){
        return service.getOrder(id);
    }

    @PostMapping("/{id}/confirm")
    public Order ConfirmOrder(UUID id){
        return service.ConfirmOrder(id);
    }


}

