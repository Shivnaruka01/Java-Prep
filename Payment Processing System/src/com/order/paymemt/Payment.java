package com.order.paymemt;

import java.math.BigDecimal;

class Payment {
	private final BigDecimal amount;
	private PaymentStatus status;
	
	public Payment(BigDecimal amount) {
		this.amount = amount;
		this.status = PaymentStatus.PENDING;
	}
	
	 public PaymentStatus getStatus() {
		return status;
	}

	 public BigDecimal getAmount() {
		return amount;
	}
	 
	 public void markAsSuccess() {
		 this.status = PaymentStatus.SUCCESS;
	 }
	 public void markAsInvalidAmount() {
		 this.status = PaymentStatus.FAILED_INVALID_AMOUNT;
	 }
	 public void markAsInsufficientFunds() {
		 this.status = PaymentStatus.FAILED_INSUFFICIENT_FUNDS;
	 }
}
