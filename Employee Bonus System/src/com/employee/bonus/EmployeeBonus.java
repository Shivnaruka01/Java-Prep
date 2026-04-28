package com.employee.bonus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeBonus {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Employee> employees = new ArrayList<>();
		
		System.out.println("Number of employees: ");
		int count = sc.nextInt();
		sc.nextLine();
		
		for(int i = 0; i < count; i++) {
			System.out.println("Enter Name: ");
			String name =sc.nextLine();
			System.out.println("Enter Salary: ");
			String salaryInput = sc.nextLine();
			BigDecimal salary = new BigDecimal(salaryInput);
			employees.add(new Employee(name, salary, null));
		}
	
		System.out.println("\n--- Employees Salary Data ---");
		employees.forEach(System.out :: println);
		
		long invalidCount = employees.stream()
							.filter(emp -> emp.performanceGrade().equals("INVALID"))
							.count();
		
		BigDecimal totalBonus = employees.stream()
							.filter(emp -> !emp.performanceGrade().equals("INVALID"))
							.map(Employee :: calculateBonus)
							.reduce(BigDecimal.ZERO, BigDecimal::add);
		 
		System.out.println("total bonus: " + totalBonus);
		System.out.println("total invalid: " + invalidCount);
		sc.close();
	}

}
