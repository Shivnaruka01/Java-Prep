package com.order.service;

public record Order(int amount, String category) {
	public Order{
		if(category == null) {
			category =  (amount <= 0) ? "INVALID" :
						(amount <= 100) ? "Small Order" :
						(amount <= 1000) ? "Medium Order" : 
						"Large Order";
		}
	}
}


/*
 * public class Order { private int amount; private String category;
 * 
 * public Order(int amount) { this.amount = amount; this.category =
 * determineCategory(amount); }
 * 
 * private String determineCategory(int amt) { if(amt <= 0) return "INVALID";
 * if(amt <= 100) return "Small Order"; if(amt <= 1000) return "Medium Order";
 * return "Large Order"; } public int getAmount() { return amount; } public
 * String getCategory() { return category; } }
 */
