package com.orderPayment.Integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrderService {
	private final PaymentService paymentService = new PaymentService();
	private final List<Payment> paymentHistory = new ArrayList<>();
	
	public  BigDecimal processOrderPayment(Order order, BigDecimal balance) {
				
		Payment payment = new Payment(order.getAmount());
		order.attachPayment(payment);
		
		BigDecimal newBalance = paymentService.processPayment(payment, balance);
		paymentHistory.add(payment);
		
		if(payment.getStatus() == PaymentStatus.SUCCESS) {
			return balance.subtract(order.getAmount());
		}
		return newBalance;	
	}
	
	public List<Payment> getPaymentHistory(){
		return List.copyOf(paymentHistory);
	}
	public BigDecimal getTotalRevenue(Order[] orders) {
		Objects.requireNonNull(orders, "Orders array cannot be null");
		return Arrays.stream(orders)
					.filter(o -> o.getPayment() != null && o.getPayment().getStatus() == PaymentStatus.SUCCESS)
					.map(Order::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<Order> getSuccessfulOrdersList(Order[] orders){
		return Arrays.stream(orders)
					.filter(o -> o.getPayment() != null && o.getPayment().getStatus() == PaymentStatus.SUCCESS)
					.toList();
	}
	
	public List<Order> getFailedOrdersList(Order[] orders) {
		return Arrays.stream(orders)
					.filter(o -> o.getPayment() != null && o.getPayment().getStatus().name().startsWith("FAILED"))
					.toList();
	}
}
