package com.orderPayment.Integration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment{
	private final String transactionId;
	private BigDecimal amount;
	private PaymentStatus status;
	private final LocalDateTime timeStamp;
	
	public Payment(BigDecimal amount) {
		if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Payment amount must be positive");
		}
		this.transactionId = UUID.randomUUID().toString();
		this.amount = amount;
		this.status = PaymentStatus.PENDING;
		this.timeStamp = LocalDateTime.now();
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	

	public void authorize() {
		if (this.status != PaymentStatus.PENDING) {
			throw new IllegalStateException("Can only authorize pending payments");
		}	
		this.status = PaymentStatus.SUCCESS;
	}

	public void decline(PaymentStatus failureReason) {
		if(!failureReason.name().startsWith("FAILED")) {
			throw new IllegalStateException("Must provide a failure status") ;
		}
		this.status = failureReason;
	}

	@Override
	public String toString() {
		return String.format("Payment [transactionId=%s, amount=%s, status=%s, timeStamp=%s]", transactionId, amount,
				status, timeStamp);
	}	
}
