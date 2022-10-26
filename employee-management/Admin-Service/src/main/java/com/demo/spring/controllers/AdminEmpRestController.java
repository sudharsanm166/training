
package com.demo.spring.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.EmployeeDTO;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.LeaveDTO;
import com.demo.spring.entity.Timesheet;
import com.demo.spring.entity.TimesheetDTO;
import com.demo.spring.exception.EmployeeNotFoundException;
import com.demo.spring.exception.LeaveExistException;
import com.demo.spring.exception.LeaveNotExistException;
import com.demo.spring.exception.TimesheetExists;
import com.demo.spring.exception.TimesheetNotFoundException;
import com.demo.spring.repository.EmpRepository;
import com.demo.spring.repository.LeaveRepository;
import com.demo.spring.repository.TimesheetRepository;
import com.demo.spring.utils.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("Admin")
@OpenAPI30
public class AdminEmpRestController {
 
	@Autowired
	EmpRepository emprepo;

	@Autowired 
	TimesheetRepository timerepo;

	@Autowired 
	LeaveRepository leaverepo;

	static Logger logger = LoggerFactory.getLogger(AdminEmpRestController.class);
 
	// add employee

	@PostMapping(path = "/add/emp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> addEmp(@RequestBody EmployeeDTO e) {

		if (emprepo.existsById(e.getEmpId())) {
			logger.error("Employee Already exists");
			return ResponseEntity.ok(new Message("Employee exists..."));

		} else {
			Employee emp = new Employee();
			emp.setEmpId(e.getEmpId());
			emp.setName(e.getName());
			emp.setCity(e.getCity());
			emp.setSalary(e.getSalary());
			emprepo.save(emp);
			logger.info("Successfully employee saved");
			return ResponseEntity.ok(new Message("Employee saved..."));
		}

	}

	// view emp

	@GetMapping(path = "/emp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.findbyid")
	public ResponseEntity findEmpById(@PathVariable("id") int id) throws EmployeeNotFoundException {
		Optional<Employee> empOp = emprepo.findById(id);
		if (empOp.isPresent()) {
			logger.info("Success response for employee");
			return ResponseEntity.ok(empOp.get());
		} else {
			logger.error("Employee not found");
			throw new EmployeeNotFoundException();
		}

	}

	// list all emp

	@GetMapping(path = "/emplist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllEmp() {
		logger.info("Success response for employee");
		return ResponseEntity.ok(emprepo.findAll());
	}

	// add timesheet

	@PostMapping(path = "/add/time", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> addTimesheet(@RequestBody TimesheetDTO t) throws TimesheetExists {

		if (timerepo.existsById(t.getTimesheetid())) {

			logger.error("Timesheet already exists");
			throw new TimesheetExists();
		} else {

			Timesheet time = new Timesheet();
			time.setEmpId(t.getEmpId());
			time.setTimesheetid(t.getTimesheetid());
			time.setType(t.getType());
			time.setLocation(t.getLocation());
			time.setHours(t.getHours());
			timerepo.save(time);
			logger.info("Success response for timesheet");
			return ResponseEntity.ok(new Message("Timesheet saved..."));
		}

	}

	// edit timesheet

	@PutMapping(path = "/edit/time", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> addTime(@RequestBody TimesheetDTO e) throws TimesheetNotFoundException {

		if (timerepo.existsById(e.getTimesheetid())) {
			Timesheet time = new Timesheet();
			time.setEmpId(e.getEmpId());
			time.setTimesheetid(e.getTimesheetid());
			time.setType(e.getType());
			time.setLocation(e.getLocation());
			time.setHours(e.getHours());
			logger.info("Success response for timesheet");
			return ResponseEntity.ok(new Message("Timesheet saved..."));

		} else {

			logger.error("Timesheet id does not exist");
			throw new TimesheetNotFoundException();
		}

	}

	// view timesheet record of an employee 

	@GetMapping(path = "/time/{eno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllByEmpno(@PathVariable("eno") int eno) {
		logger.info("Success response for timesheet");
		return ResponseEntity.ok(timerepo.findAllByEmpId(eno));
	}

	// list all timesheet

	@GetMapping(path = "/timelist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllTimesheet() {
		logger.info("Success response for timesheet");
		return ResponseEntity.ok(timerepo.findAll());
	}

	// apply leave

	@PostMapping(path = "/add/leave", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> addLeave(@RequestBody LeaveDTO l) throws LeaveExistException {

		if (leaverepo.existsById(l.getLeaveid())) {

			logger.error("Leave id already exists");
			throw new LeaveExistException();
		} else {

			Leave leave = new Leave();
			leave.setEmpId(l.getEmpId());
			leave.setLeaveid(l.getLeaveid());
			leave.setNoofdays(l.getNoofdays());
			leave.setReason(l.getReason());
			leave.setStatus(l.getStatus());
			leaverepo.save(leave);
			logger.info("Success response for leave");
			return ResponseEntity.ok(new Message("Leave applied...."));
		}

	}

	// approve leave

	@PutMapping(path = "/approve/leave", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> approveLeave(@RequestBody LeaveDTO l) throws LeaveNotExistException {

		if (leaverepo.existsById(l.getLeaveid())) {
			Leave leave = new Leave();
			leave.setEmpId(l.getEmpId());
			leave.setLeaveid(l.getLeaveid());
			leave.setNoofdays(l.getNoofdays());
			leave.setReason(l.getReason());
			leave.setStatus(l.getStatus());
			leaverepo.save(leave);
			logger.info("Success response for leave");
			return ResponseEntity.ok(new Message("Leave Status changed..."));

		} else {

			logger.error("Leave id does not exist");
			throw new LeaveNotExistException();
		}

	}

	// view all leaves

	@GetMapping(path = "/leavelist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllLeaves() {
		logger.info("Success response for leave");
		return ResponseEntity.ok(leaverepo.findAll());
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Message> handleEmpNotFound(EmployeeNotFoundException ex) {
		return ResponseEntity.ok(new Message("Employee not found.."));
	} 

	@ExceptionHandler(TimesheetExists.class)
	public ResponseEntity<Message> handleTimesheetExist(TimesheetExists ex) {
		return ResponseEntity.ok(new Message("Timesheet Exists.."));
	}

	@ExceptionHandler(TimesheetNotFoundException.class)
	public ResponseEntity<Message> handleTimesheetNotFound(TimesheetNotFoundException ex) {
		return ResponseEntity.ok(new Message("Timesheet Not Found.."));
	}

	@ExceptionHandler(LeaveExistException.class)
	public ResponseEntity<Message> handleLeaveExist(LeaveExistException ex) {
		return ResponseEntity.ok(new Message("Leave Id already exists.."));
	}

	@ExceptionHandler(LeaveNotExistException.class)
	public ResponseEntity<Message> handleLeaveExist(LeaveNotExistException ex) {
		return ResponseEntity.ok(new Message("Leave Id Not exists.."));
	}

}
