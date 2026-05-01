package com.orderPayment.Integration;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentService {
	
	public BigDecimal processPayment(Payment payment, BigDecimal currentBalance) {
		Objects.requireNonNull(payment, "Payment entity cannot be null");
		Objects.requireNonNull(currentBalance, "Balance cannot be null");
		
		if(currentBalance.compareTo(payment.getAmount()) >= 0) {
			payment.authorize();
			return currentBalance.subtract(payment.getAmount());
		}else {
			payment.recordFailure("Insufficient funds in wallet");
			return currentBalance;
		}
	}
}
