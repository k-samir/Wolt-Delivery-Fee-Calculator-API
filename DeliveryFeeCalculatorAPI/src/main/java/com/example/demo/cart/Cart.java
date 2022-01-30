package com.example.demo.cart;
import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;


public class Cart {
    //Value of the shopping cart in cents.
    private Integer value;
    // The distance between the store and customer’s location in meters
    private Integer delivery_distance;
    // The number of items in the customer's shopping cart.
    private Integer number_of_items;
    private Calendar time;

    private Integer delivery_fee = -1;

    public Calendar getTime() {
        return time;
    }

    public Cart(Integer value, Integer delivery_distance, Integer number_of_items, String time) {
        this.value = value;
        this.delivery_distance = delivery_distance;
        this.number_of_items = number_of_items;
        this.time = DatatypeConverter.parseDateTime(time);

        this.delivery_fee = this.initDeliveryFee();

    }

    public Integer getDelivery_fee() {
        return delivery_fee;
    }

    public Integer initDeliveryFee() {
        int res = 0;
        if(this.value>= 10000) {
            return 0;
        }


        if(this.value < 1000) {
            res +=  1000 - this.value;
        }

        // base delivery fee
        res += 200;

        int remaining = this.delivery_distance - 1000;
        if(remaining > 0) {

            res+= (remaining/500)*100;
            if(remaining%500 != 0) {
                res+=100;
            }
        }

        if(this.number_of_items >= 5) {
            res+= (this.number_of_items-4)*50;
        }

        // Checking for rush day and hour : Firday -> 6
        if(this.time.get(Calendar.DAY_OF_WEEK) == 6 && (this.time.get(Calendar.HOUR_OF_DAY) > 15 && this.time.get(Calendar.HOUR_OF_DAY) < 19)) {

            res*= 1.1;
        }

        if(res > 1500) {
            res = 1500;
        }

        return res;
    }




}

