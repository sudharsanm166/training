package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {
	public List<Timesheet> findAllByEmpId(int time);

}
