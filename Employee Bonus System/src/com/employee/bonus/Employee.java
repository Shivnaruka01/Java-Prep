package com.employee.bonus;

import java.math.BigDecimal;

public record Employee(String name, BigDecimal salary, String performanceGrade) {
	private static final BigDecimal MID_SALARY_THRESHOLD = new BigDecimal("5000");
	private static final BigDecimal HIGH_SALARY_THRESHOLD = new BigDecimal("10000");
	
	public Employee {
	if(performanceGrade == null) {
	performanceGrade = (salary.compareTo(BigDecimal.ZERO) <= 0) ? "INVALID" :
					   (salary.compareTo(MID_SALARY_THRESHOLD) <= 0) ? "C" :
					   (salary.compareTo(HIGH_SALARY_THRESHOLD) <= 0) ? "B" : "A";						
		}
	}
	public BigDecimal calculateBonus() {
		return switch(performanceGrade) {
		case "A" -> salary.multiply(new BigDecimal("0.20"));
		case "B" -> salary.multiply(new BigDecimal("0.10"));	
		default -> BigDecimal.ZERO;
		};
	}

}
