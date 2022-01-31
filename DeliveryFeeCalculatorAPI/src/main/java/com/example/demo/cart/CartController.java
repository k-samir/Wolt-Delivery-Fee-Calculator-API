package com.example.demo.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/deliveryFee")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<Map<String, Object>> deliveryFee(@RequestBody Cart cart){
        Map<String, Object> payload = new HashMap<>();
        payload.put("delivery_fee",cartService.getDeliveryFee(cart));
        return ResponseEntity.ok(payload);
    }

}
