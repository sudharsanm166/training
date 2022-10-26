package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LEAVES")
public class Leave {
	@Id 
	@Column(name="LEAVEID")
	private int leaveid;
	@Column(name="EMPNO")
	private int empId;
	@Column(name="NOOFDAYS")
	private int noofdays;
	@Column(name="REASON")
	private String reason;
	@Column(name="STATUS")
	private String status;

	public Leave() {
	}

	public Leave(int empId, int leaveid, int noofdays, String reason, String status) {
		super();
		this.empId = empId;
		this.leaveid = leaveid;
		this.noofdays = noofdays;
		this.reason = reason;
		this.status = status;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getLeaveid() {
		return leaveid;
	}

	public void setLeaveid(int leaveid) {
		this.leaveid = leaveid;
	}

	public int getNoofdays() {
		return noofdays;
	}

	public void setNoofdays(int noofdays) {
		this.noofdays = noofdays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
