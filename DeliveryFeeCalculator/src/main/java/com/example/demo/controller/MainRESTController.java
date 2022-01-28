package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.CartDAO;
import com.example.demo.model.Cart;

public class MainRESTController {

	 @Autowired
	 private CartDAO cartDAO;

	 @RequestMapping(value = "/", method = RequestMethod.GET)
	 @ResponseBody
	 public String welcome() {
	     return "Welcome to RestTemplate Example.";
	 }

	 // URL:
	 // http://localhost:8080/employee

	 @RequestMapping(value = "/deliveryFee", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	 @ResponseBody
	 public Integer getDeliveryFee(@RequestBody Cart cart) {
	     System.out.println("(Service Side) Creating employee: " + cart.toString());
	     return cartDAO.getDeliveryFee(cart);
	 }
    
}
