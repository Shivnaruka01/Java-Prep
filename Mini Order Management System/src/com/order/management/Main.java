package com.order.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int[] orders = new int [5];
		int invalidCount = 0;
		
		for(int i = 0; i < orders.length; i++) {
			System.out.println("Enter amount of order: " + (i + 1));
			orders[i] = sc.nextInt();
		}
		
		List<Integer> validOrders = new ArrayList<>();
		
		System.out.println("\n---Order  Summary---");
		for(int amount : orders) {
			if(validateOrder(amount)) {
				validOrders.add(amount);
				System.out.println("Order " + amount + " : " + categorizeOrder(amount));
			}else {
				invalidCount++;
				System.out.println("Order " + amount + " : " + "INVALID");
			}
		}
		
		int total = calculateTotal(validOrders);
		
		System.out.println("\nTotal Order Value : " + total);
		System.out.println("Count of Invalid Orders : " + invalidCount);
		
		sc.close();
	}

	private static int  calculateTotal(List<Integer> validateOrders) {
		int sum = 0;
		for(int val : validateOrders) {
			sum += val;
			
		}
		return sum;
	}

	private static String categorizeOrder(int amount) {
		if(amount <= 100) return "Small Order";
		if(amount <= 1000) return "Medium Order";
		return "Large Order";
	}

	private static boolean validateOrder(int amount) {
		return amount > 0;
	}

}
