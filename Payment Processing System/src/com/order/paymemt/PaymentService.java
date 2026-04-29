package com.order.paymemt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class PaymentService {

	public static BigDecimal processPayment(Payment payment, BigDecimal balance) {
		BigDecimal amount = payment.getAmount();

		if (amount.compareTo(BigDecimal.ZERO) <= 0) { 
			payment.markAsInvalidAmount();
			return balance;
		}
		if(amount.compareTo(balance) > 0) {
			payment.markAsInsufficientFunds();
			return balance;
		}

		payment.markAsSuccess();
		return balance.subtract(amount);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter initial wallet balance: ");
		BigDecimal balance = sc.nextBigDecimal().setScale(2, RoundingMode.HALF_UP);

		System.out.println("How many payments would you like to process? : ");
		int numPayments = sc.nextInt();
		Payment[] payments = new Payment[numPayments];

		for (int i = 0; i < numPayments; i++) {
			System.out.println("Enter amount for payment " + (i + 1) + " : ");
			BigDecimal amt = sc.nextBigDecimal();

			payments[i] = new Payment(amt);
			balance = processPayment(payments[i], balance);
		}

		int successfulCount = 0, failedCount = 0;
		BigDecimal total = BigDecimal.ZERO;
		System.out.println("\n--- Transaction Log ---");
		for(Payment p : payments) {
			System.out.println("Amount: $" + p.getAmount() + " | Status: " + p.getStatus());
			if(p.getStatus() == PaymentStatus.SUCCESS) {
				successfulCount++;
				total = total.add(p.getAmount());
			}else {
				failedCount++;
			}
		}
		
		System.out.println("\n--- Detailed Payment Report ---");
		System.out.println("Remaining Balance: $" + balance);
		System.out.println("Total Amount: $" + total);
		System.out.println("Success Count: " + successfulCount + " | Failure Count: " + failedCount);

		sc.close();
	}
}
