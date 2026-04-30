package com.orderPayment.Integration;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentService {
	
	public BigDecimal processPayment(Payment payment, BigDecimal balance) {
		Objects.requireNonNull(payment, "Payment entity cannot be null");
		
		if(balance.compareTo(payment.getAmount()) >= 0) {
			payment.authorize();
			return balance.subtract(payment.getAmount());
		}else {
			payment.decline(PaymentStatus.FAILED_INSUFFICIENT_FUNDS);
			return balance;
		}
	}

}
