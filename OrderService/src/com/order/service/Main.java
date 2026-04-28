package com.order.service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		int[] inputAmount = new int[5];
		
		// Collect user input
		for(int i = 0; i < inputAmount.length; i++) {
			System.out.println("Enter amount for order " + (i+1) + ": ");
			inputAmount[i] = sc.nextInt();
		}
		
		// Convert amount to Order objects
		List<Order> orders = Arrays.stream(inputAmount)
							.mapToObj(amt -> new Order(amt, null))	// Constructor handles categorization
							.toList();
		
		// Print each order
		System.out.println("\n--- order Summary ---");
		orders.forEach(System.out :: println);
		
		// Calculation Using Steams
		long invalidCount = orders.stream()
						.filter(o -> o.category().equals("INVALID"))
						.count();
		
		long total = orders.stream()
					.filter(o -> !o.category().equals("INVALID"))
					.mapToLong(Order :: amount)
					.sum();
		
		System.out.println("Total Value: " + total);
		System.out.println("Invalid Orders Found: " + invalidCount);
		
		sc.close();
	}
	
}
