package com.orderPayment.Integration;

public enum PaymentStatus {
	PENDING, SUCCESS, FAILED_RETRYABLE, FAILED_PERMANENTLY;

	public boolean isFailed() {
		return this == FAILED_RETRYABLE || this == FAILED_PERMANENTLY;
	}
}
