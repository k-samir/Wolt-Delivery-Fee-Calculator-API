package com.example.demo.cart;

import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CartService {

    public Integer getDeliveryFee(Cart cart) {
        return cart.getDelivery_fee();
    }
}
