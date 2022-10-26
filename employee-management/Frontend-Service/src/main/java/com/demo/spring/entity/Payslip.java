package com.demo.spring.entity;

public class Payslip {
	private int empId;
	private int payRollId;
	private String month;
	private int paydays;
	private double salary;
	
	public Payslip() {
		
	}

	public Payslip(int empId, int payRollId, String month, int paydays, double salary) {
		super();
		this.empId = empId;
		this.payRollId = payRollId;
		this.month = month;
		this.paydays = paydays;
		this.salary = salary;
	}

	public int getEmpId() {
		return empId;
	}



	public int getPayRollId() {
		return payRollId;
	}



	public String getMonth() {
		return month;
	}


	public int getPaydays() {
		return paydays;
	}


	public double getSalary() {
		return salary;
	}



}
