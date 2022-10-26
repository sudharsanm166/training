package com.demo.spring.DTO;

public class LeaveDTO {
	private int empId;
	private int leaveid;
	private int noofdays;
	private String reason;
	private String status;
 
	public LeaveDTO() {
	}

	public LeaveDTO(int empId, int leaveid, int noofdays, String reason, String status) {
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
