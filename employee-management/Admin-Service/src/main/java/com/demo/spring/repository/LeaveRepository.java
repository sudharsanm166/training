package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {

}
