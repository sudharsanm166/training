package com.demo.spring.DTO;

public class PayslipDTO {
	
	private int empId;
	private int payRollId;
	private String month;
	private int paydays;
	private double salary;
	
	public PayslipDTO() {
	}

	public PayslipDTO(int empId, int payRollId, String month, int paydays, double salary) {
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

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getPayRollId() {
		return payRollId;
	}

	public void setPayRollId(int payRollId) {
		this.payRollId = payRollId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getPaydays() {
		return paydays;
	}

	public void setPaydays(int paydays) {
		this.paydays = paydays;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
