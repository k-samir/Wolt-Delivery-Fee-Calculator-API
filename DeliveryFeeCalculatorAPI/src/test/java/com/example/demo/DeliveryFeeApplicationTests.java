package com.example.demo;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartController;
import com.example.demo.cart.CartService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DeliveryFeeApplicationTests {

	@Autowired
	private CartController cartController;

	// If the cart value is less than 10€, a small order surcharge is added to the delivery price.
	// The surcharge is the difference between the cart value and 10€.
	@Test
	public void testSurcharge() {
		Cart cart = new Cart(890,900,4,"2021-01-16T13:00:00Z");
		Assert.assertEquals(310,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}


	@Test
	public void testPostDeliveryFee1() {
		Cart cart = new Cart(790,2235,4,"2021-01-16T13:00:00Z");
		Assert.assertEquals(710,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	// Example 1: If the delivery distance is 1499 meters, the delivery fee is: 2€ base fee + 1€ for
	// the additional 500 m => 3€
	@Test
	public void testDistance1() {
		Cart cart = new Cart(1100,1499,4,"2021-01-16T13:00:00Z");
		Assert.assertEquals(300,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	// Example 2: If the delivery distance is 1500 meters, the delivery fee is: 2€ base fee + 1€ for the additional
	// 500 m => 3€
	@Test
	public void testDistance2() {
		Cart cart = new Cart(1100,1500,4,"2021-01-16T13:00:00Z");
		Assert.assertEquals(300,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	//Example 3: If the delivery distance is 1501 meters, the delivery fee is: 2€ base fee + 1€ for the first 500 m +
	// 1€ for the second 500 m => 4€
	@Test
	public void testDistance3() {
		Cart cart = new Cart(1100,1501,4,"2021-01-16T13:00:00Z");
		Assert.assertEquals(400,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	//Example 2: If the number of items is 5, 50 cents surcharge is added
	@Test
	public void testItems1() {
		Cart cart = new Cart(1100,1501,5,"2021-01-16T13:00:00Z");
		Assert.assertEquals(450,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}
	// Example 3: If the number of items is 10, 3€ surcharge (6 x 50 cents) is added
	@Test
	public void testItems2() {
		Cart cart = new Cart(1100,1501,10,"2021-01-16T13:00:00Z");
		Assert.assertEquals(700,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	// The delivery fee can never be more than 15€, including possible surcharges.
	@Test
	public void testLimit() {
		Cart cart = new Cart(1100,1501,100,"2021-01-16T13:00:00Z");
		Assert.assertEquals(1500,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	// The delivery is free (0€) when the cart value is equal or more than 100€.
	@Test
	public void testFreeShipping1() {
		Cart cart = new Cart(10000,1501,4,"2021-01-16T13:00:00Z");
		Assert.assertEquals(0,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}
	@Test
	public void testFreeShipping2() {
		Cart cart = new Cart(9999,1501,4,"2021-01-16T13:00:00Z");
		Assert.assertNotEquals(0,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}
	@Test
	public void testFreeShipping3() {
		Cart cart = new Cart(10001,1501,4,"2021-01-16T13:00:00Z");
		Assert.assertEquals(0,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	//During the Friday rush (3 - 7 PM UTC), the delivery fee (the total fee including possible surcharges) will be
	// multiplied by 1.1x. However, the fee still cannot be more than the max (15€)
	@Test
	public void testRushFee() {
		Cart cart = new Cart(890,900,4,"2022-01-28T16:00:00Z");
		Assert.assertEquals(341,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}
	// It's is 2:59 so not rush hour
	@Test
	public void testNoRushFee1() {
		Cart cart = new Cart(890,900,4,"2022-01-28T14:59:59Z");
		Assert.assertEquals(310,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}
	// It's is not friday so no rush fee
	@Test
	public void testNoRushFee2() {
		Cart cart = new Cart(890,900,4,"2022-01-29T16:00:00Z");
		Assert.assertEquals(310,cartController.deliveryFee(cart).getBody().get("delivery_fee"));
	}

	@Test
	void contextLoads() {
	}
}
