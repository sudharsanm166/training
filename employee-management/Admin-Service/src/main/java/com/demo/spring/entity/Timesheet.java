package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIMESHEET")
public class Timesheet {
	@Id
	@Column(name="TIMESHEETID")
	private int timesheetid;
	@Column(name="EMPNO")
	private int empId;
	@Column(name="TYPE")
	private String type;
	@Column(name="LOCATION")
	private String location;
	@Column(name="HOURS")
	private int hours;

	public Timesheet() {
	}

	public Timesheet(int empId, int timesheetid, String type, String location, int hours) {
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