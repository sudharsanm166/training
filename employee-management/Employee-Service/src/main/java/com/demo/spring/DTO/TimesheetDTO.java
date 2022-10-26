package com.demo.spring.DTO;

public class TimesheetDTO {
	
	private int empId;
	private int timesheetid;
	private String type;
	private String location;
	private int hours;

	public TimesheetDTO() { 
	}

	public TimesheetDTO(int empId, int timesheetid, String type, String location, int hours) {
		super();
		this.empId = empId;
		this.timesheetid = timesheetid;
		this.type = type;
		this.location = location;
		this.hours = hours;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getTimesheetid() {
		return timesheetid;
	}

	public void setTimesheetid(int timesheetid) {
		this.timesheetid = timesheetid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

}
