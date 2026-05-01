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

		for (int i = 0; i < orderCount; i++) {
			System.out.println("\n--- Processing Order #" + (i + 1) + " ---");
			int id = readInt(sc, "Enter Order Id: ");
			BigDecimal price = readPrice(sc, "Enter Order Price: ");

			Order order = new Order(id, price);
			orderList.add(order);

			Payment p = null;
			boolean userAborted = false;

			do {
				walletBalance = orderService.processPaymentForOrder(order, walletBalance);
				p = order.getPayment();

				if (p != null && p.getStatus() == PaymentStatus.SUCCESS) {
					System.out.println("Payment Successful!!!");
					break;
				}

				if (p != null && p.isRetryable() && !userAborted) {
					System.out.println("Attempt Failed: " + p.getFailureReason());
					System.out.print("Current Balance: $" + walletBalance + ". Top up and retry? (yes/no): ");

					if (sc.next().equalsIgnoreCase("yes")) {
						walletBalance = walletBalance.add(readPrice(sc, "Amount to add: "));
					} else {
						userAborted = true;
					}
				}
			} while (p != null && p.isRetryable() && !userAborted);
			if (p != null && p.getStatus() == PaymentStatus.FAILED_PERMANENTLY) {
				System.out.println("Permanent Failure: " + p.getFailureReason());
			}
		}
		Order[] finalOrders = orderList.toArray(new Order[0]);
		printFinalReport(orderService, finalOrders);

		sc.close();
	}

	private static void printFinalReport(OrderService orderService, Order[] finalOrders) {
		List<Order> successList = orderService.getSuccessfulOrdersList(finalOrders);
		List<Order> failedist = orderService.getFailedOrdersList(finalOrders);

		System.out.println("Total Revenue: $" + orderService.getTotalRevenue(finalOrders));
		System.out.println("Successful Payments Count: " + successList.size());
		System.out.println("Failed Payments Count: " + failedist.size());

		System.out.println("\n--- FULL PAYMENT AUDIT TRAIL ---");
		orderService.getPaymentHistory().forEach(System.out::println);
	}

	private static BigDecimal readPrice(Scanner scanner, String prompt) {
		while (true) {
			System.out.println(prompt);
			if (scanner.hasNextBigDecimal()) {
				BigDecimal value = scanner.nextBigDecimal();
				if (value.compareTo(BigDecimal.ZERO) >= 0) {
					return value;
				}
				System.out.println("Error: Amount cannot be negative");
			} else {
				System.out.println("Error: Invalid input. Please enter a number");
				scanner.next();
			}
		}
	}

	private static int readInt(Scanner scanner, String prompt) {
		while (true) {
			System.out.println(prompt);
			if (scanner.hasNextInt()) {
				int value = scanner.nextInt();
				if (value > 0) {
					return value;
				}
				System.out.println("Error: Must be positive whole number");
			} else {
				System.out.println("Error: Invalid input. Please enter a valid whole number");
				scanner.next();
			}
		}
	}
}
