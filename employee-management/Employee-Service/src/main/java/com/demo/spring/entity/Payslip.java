package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PAYSLIP")
public class Payslip {
	@Id
	@Column(name="PAYROLLID")
	private int payRollId;
	@Column(name="EMPNO")
	private int empId;
	@Column(name="MONTH")
	private String month;
	@Column(name="PAYDAYS")
	private int paydays;
	@Column(name="SALARY")
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
