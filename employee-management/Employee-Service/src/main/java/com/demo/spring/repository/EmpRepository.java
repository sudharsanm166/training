package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Employee;

public interface EmpRepository extends JpaRepository<Employee, Integer> {

}
