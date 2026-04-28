package com.atm.withdrawal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AtmWithdrawalSystem {
	private static final int MAX_FAILED_ATTEMPTS = 3;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long balance = 10000;
		boolean run = true;
		int failAttempt = 0;
		
		while(run) {
			System.out.println("\n----ATM MENU----");
			System.out.println("1. Withdraw");
			System.out.println("0. Exit");
			System.out.print("Choose option: ");
			
			String choice = sc.nextLine();
			switch (choice) {
			case "1" -> {
					System.out.println("Enter amount to withdraw: ");
						 try{
							  long amount = sc.nextLong();
			
						  if(amount <= 0) {
							  System.out.println("Invalid amount");
						  }else if(amount > balance) {
							  failAttempt++;
							  System.out.println("Insufficient balance");
							  if(failAttempt >= MAX_FAILED_ATTEMPTS) {
								  System.out.println("Suspicious Activity Detected");
								  run = false;
							  }
						  }else {
							  balance -= amount;
							  System.out.println("Success! Remaining balance: " + balance);
							  failAttempt = 0;
						  }
						  }catch(InputMismatchException e) {
							  System.out.println("Please enter numbers only");
							  sc.nextLine();
						  }
			}
			case "0" -> {
				System.out.println("Thank you for using ATM!");
				run = false;
			}
					
			
			default -> System.out.println("invalid choice");
				
			}
		}
		sc.close();
	}

}
