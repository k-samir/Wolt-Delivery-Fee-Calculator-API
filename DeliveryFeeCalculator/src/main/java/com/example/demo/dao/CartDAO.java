package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Cart;

@Repository
public class CartDAO {

    static {
    }
    
    public Integer getDeliveryFee(Cart cart) {
        return cart.getDeliveryFee();
    }

}
