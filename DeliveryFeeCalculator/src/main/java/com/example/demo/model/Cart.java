package com.example.demo.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Cart {
	 //Value of the shopping cart in cents.
	 private Integer value;
	 // The distance between the store and customer’s location in meters
	 private Integer delivery_distance;
	 // The number of items in the customer's shopping cart.
	 private Integer number_of_items;
	 private Calendar time;
	 
	public Cart(Integer value, Integer delivery_distance, Integer number_of_items, Date date) {
		this.value = value;
		this.delivery_distance = delivery_distance;
		this.number_of_items = number_of_items;
		
		this.time = GregorianCalendar.getInstance(); 
		this.time.setTime(date);
		
	}

	public int getDeliveryFee() {
		int res = 0;
		if(this.value>= 10000) {
			return 0;
		}
		
		
		if(this.value < 1000) {
			res +=  1000 - this.value;
		}
				
		// base delivery fee
		res += 2;
		
		int remaining = this.delivery_distance - 1000;
		if(remaining > 0) {
			
			res+= (remaining/500)*1;
			if(remaining%500 != 0) {
				res+=1;
			}		
		}
		
		if(this.number_of_items >= 5) {
			res+= (this.number_of_items-4)*0.5;
		}
		
		
		if(this.time.get(Calendar.DAY_OF_WEEK) == 5 && (this.time.get(Calendar.HOUR_OF_DAY) > 15 && this.time.get(Calendar.HOUR_OF_DAY) < 19)) {
			res*= 1.1;
		}
		
		if(res > 15) {
			res = 15;
		}
		
		return res;
	}
	
	@Override
	public String toString(){
		return "Cart : " + this.number_of_items + " items , " + this.value + "€";
	}
	
	

  
}

