package com.example.orders; 

import jakarta.prsistence.*; 
import java.util.UUID; 


/*  Un ordine è una richiesta di un cliente di acquistare dei prodotti. 



*/
@Entity
Public class Order{
    
    @Id
    @GenerateValue
    private UUID id; 

    @Column(nullable = false)
    private UUID customerId; 

    @Enumarate(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status; 

    @Column(nullable = false)
    private double totale; 


    //Server a JPA il costruttore vuoto
    protected Order(){}

    public Order(UUID customerId, double totale){
        this.customerId = customerId;
        this.totale = totale; 
        this.status = OrderStatus.CREATED; //Inizializzato a Creato 
    }


    //----------GETTER-------------

    public UUID getId(){
        return id;
    }
    
    public UUID getCustomerId(){
        return customerId;
    }

    public OrderStatus(){
        return status;
    }

    public double getTotale(){
        return totale;
    }

    //----------- SETTER -------------
    public void setCustomerId(UUID customerId){
        this.customerId = customerId;
    }

    public void setId(UUID id){
        this.id = id;
    } 

    public void setStatus(OrderStatus status){
        this.status = status; 
    }

    public void setTotale(double totale){
        this.totale = totale; 
    }
    
}