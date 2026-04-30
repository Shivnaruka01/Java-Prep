package com.orderPayment.Integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		OrderService orderService = new OrderService();
		List<Order> orderList = new ArrayList<>();
		
		BigDecimal walletBalance = readPrice(sc, "Enter your wallet balance: ");
		
		int orderCount = readInt(sc, "How many orders would you like to create? ");
		
		for(int i = 0; i < orderCount; i++) {
			
			int id = readInt(sc, "Enter Order Id: ");
			BigDecimal price = readPrice(sc, "Enter Order Price: ");
			
			Order order = new Order(id, price);
			orderList.add(order);
				
			walletBalance = orderService.processOrderPayment(order, walletBalance);
				
			System.out.println("Status: " + order.getPayment().getStatus());
			System.out.println("Remaining Balance: " + walletBalance  );
		}
		
		Order[] finalOrders = orderList.toArray(new Order[0]);
		printFinalReport(orderService, finalOrders);
		
		System.out.println("\n--- SUCCESSFUL ORDERS LIST ---");
		List<Order> successList = orderService.getSuccessfulOrdersList(finalOrders);
		List<Order> failedist = orderService.getSuccessfulOrdersList(finalOrders);
		
		System.out.println("Successful Payments Count: " + successList.size());
		System.out.println("Failed Payments Count: " + failedist.size());
		
		if(successList.isEmpty()) {
			System.out.println("No successful transaction.");
		}else {
			successList.forEach(o -> System.out.println("Order ID: " + o.getId() + " | Amount: $" + o.getAmount()));
		}
		
		 System.out.println("\n--- FULL PAYMENT AUDIT TRAIL ---");
		 orderService.getPaymentHistory().forEach(System.out::println);
		
		sc.close();
	}

	private static void printFinalReport(OrderService orderService, Order[] finalOrders) {
		System.out.println("Total Revenue: $" + orderService.getTotalRevenue(finalOrders));
		System.out.println("Failed Payments: " + orderService.getFailedOrdersList(finalOrders));
		System.out.println("Successful Payments: " + orderService.getSuccessfulOrdersList(finalOrders));
		
	}
	
	private static BigDecimal readPrice(Scanner scanner, String prompt) {
		while(true) {
			System.out.println(prompt);
			if(scanner.hasNextBigDecimal()) {
				BigDecimal value = scanner.nextBigDecimal();
				if(value.compareTo(BigDecimal.ZERO) >= 0) {
					return value;
				}
				System.out.println("Error: Amount cannot be negative");
			}else {
				System.out.println("Error: Invalid input. Please enter a number");
				scanner.next();
			}
		}
	}
	
	private static int readInt(Scanner scanner, String prompt) {
		while(true) {
			System.out.println(prompt);
			if(scanner.hasNextInt()) {
				int value = scanner.nextInt();
				if(value > 0) {
					return value;
				}
				System.out.println("Error: Must be positive whole number");
			}else {
				System.out.println("Error: Invalid input. Please enter a valid whole number");
				scanner.next();
			}
		}
	}
	
}
