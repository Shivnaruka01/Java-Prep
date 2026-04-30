package com.orderPayment.Integration;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
	private final int id;
	private final BigDecimal amount;
	private Payment payment;

	public Order(int id, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Order amount must be positive");
		}
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void attachPayment(Payment payment) {
		if(this.payment != null) {
			throw new IllegalStateException("Payment already attached to order " + id);
		}
		this.payment = Objects.requireNonNull(payment);
	}
	
	public Payment getPayment() { return payment; }
}
