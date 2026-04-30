package com.orderPayment.Integration;

public enum PaymentStatus {
	PENDING,
	SUCCESS,
    FAILED_INVALID_AMOUNT, 
	FAILED_INSUFFICIENT_FUNDS,
	FAILED_UNAUTHORIZED
}
