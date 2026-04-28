package com.order.validation;

import java.util.Scanner;

public class OrderQuantityValidator {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		//ArrayList<String> validProducts = new ArrayList<String>();
		
		System.out.println("Enter quantity of products you want to buy: ");
		int numberOfEntries = Integer.parseInt(scanner.nextLine());
		
		int invalidCount = 0;
		int validCount = 0;	//  Counts how many products were valid (the divisor)
		int totalValidQuantity = 0;	// sum of all items in those valid orders.
		
		for(int i = 1; i <= numberOfEntries; i++) {	
			System.out.println("Enter product name: ");
			String productName = scanner.nextLine();
			
			System.out.println("Enter quantity of product " + productName + ": ");
			int quantity = Integer.parseInt(scanner.nextLine());
			
			if(quantity <= 0) {
				System.out.println("Result: Invalid Quantity");
				invalidCount++;
			}else {
				validCount++;
				totalValidQuantity += quantity;
				if(quantity <= 10) {
					System.out.println("Result: Normal Order");
				}else if(quantity <= 100) {
					System.out.println("Result: Bulk Order");
				}else {
					System.out.println("Result: Wholesale - Special Handling");
				}
				
			}
		}

		System.out.println("Invalid Count: " + invalidCount);
		System.out.println("Valid Orders: " + validCount + " | Total Items: " + totalValidQuantity);
		if(validCount > 0) {
			double avg = (double) totalValidQuantity / validCount;
			System.out.println("Average valid quantity: " + avg);
		}else {
			System.out.println("Average valid quantity: 0 (No valid products)");
		}
		scanner.close();
	}

}
