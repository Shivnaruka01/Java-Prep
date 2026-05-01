package com.orderPayment.Integration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
	private final String transactionId;
	private final BigDecimal amount;
	private PaymentStatus status;
	private final LocalDateTime timeStamp;
	private int retryCount = 0;
	private String failureReason;
	private static final int MAX_RETRIES = 2;

	public Payment(BigDecimal amount) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
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

	public int getTotalAttempts() {
		return retryCount + 1;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void authorize() {
		if (this.status == PaymentStatus.SUCCESS || this.status == PaymentStatus.FAILED_PERMANENTLY) {
			throw new IllegalStateException("Can only authorize pending payments");
		}
		this.status = PaymentStatus.SUCCESS;
		this.failureReason = null;
	}

	public void recordFailure(String reason) {
		this.failureReason = reason;
		if (this.retryCount < MAX_RETRIES) {
			this.status = PaymentStatus.FAILED_RETRYABLE;
			this.retryCount++;
		} else {
			this.status = PaymentStatus.FAILED_PERMANENTLY;
		}
	}

	public boolean isRetryable() {
		return this.status == PaymentStatus.FAILED_RETRYABLE;
	}

	@Override
	public String toString() {
		return String.format(
				"Payment [transactionId=%s, amount=%s, status=%s, timeStamp=%s, retryCount=%s, failureReason=%s]",
				transactionId, amount, status, timeStamp, retryCount, failureReason);
	}
}
