
package com.demo.spring.controllers;

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

import com.demo.spring.DTO.EmployeeDTO;
import com.demo.spring.DTO.LeaveDTO;
import com.demo.spring.DTO.TimesheetDTO;
import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Timesheet;
import com.demo.spring.exception.EmployeeNotFoundException;
import com.demo.spring.exception.LeaveExistException;
import com.demo.spring.exception.TimesheetExistsException;
import com.demo.spring.repository.EmpRepository;
import com.demo.spring.repository.LeaveRepository;
import com.demo.spring.repository.PayslipRepository;
import com.demo.spring.repository.TimesheetRepository;
import com.demo.spring.utils.Message;

import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("Employee")
@OpenAPI30
public class EmpRestController {

	@Autowired
	EmpRepository emprepo;

	@Autowired
	TimesheetRepository timerepo;

	@Autowired
	LeaveRepository leaverepo;

	@Autowired
	PayslipRepository payrepo;

	static Logger logger = LoggerFactory.getLogger(EmpRestController.class);

	// ----------------edit profile--------------------//

	@PutMapping(path = "/edit/emp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> addEmp(@RequestBody EmployeeDTO e) throws EmployeeNotFoundException {

		if (emprepo.existsById(e.getEmpId())) {
			Employee emp = new Employee();
			emp.setEmpId(e.getEmpId());
			emp.setName(e.getName());
			emp.setCity(e.getCity());
			emp.setSalary(e.getSalary());
			emprepo.save(emp);
			logger.info("Success response for employee");
			return ResponseEntity.ok(new Message("Employee saved"));

		} else {

			logger.error("Employee does not exist");
			throw new EmployeeNotFoundException();
		}

	}

	// ------------add timesheet---------------//

	@PostMapping(path = "/add/time", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> addTimesheet(@RequestBody TimesheetDTO t) throws TimesheetExistsException {

		if (timerepo.existsById(t.getTimesheetid())) {

			logger.error("Timesheet id Already exists");
			throw new TimesheetExistsException();
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

	// ----------------apply leave------------------//

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
			return ResponseEntity.ok(new Message("Leave applied..."));
		}

	}

	// ------------view timesheet record-----------------//

	@GetMapping(path = "/time/list/{eno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllByEmpno(@PathVariable("eno") int eno) {
		logger.info("Success response for timesheet");
		return ResponseEntity.ok(timerepo.findAllByEmpId(eno));
	}

	// ------------------view payslip record---------------//

	@GetMapping(path = "/pays/list/{eno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllByEmpno1(@PathVariable("eno") int eno) {
		logger.info("Success response for payslip");
		return ResponseEntity.ok(payrepo.findAllByEmpId(eno));
	}

	// ----------------view leaves record-----------------------//

	@GetMapping(path = "/leave/{eno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllByEmpno3(@PathVariable("eno") int eno) {
		logger.info("Success response for leave");
		return ResponseEntity.ok(leaverepo.findAllByEmpId(eno));
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Message> handleEmpNotFound(EmployeeNotFoundException ex) {
		return ResponseEntity.ok(new Message("Employee Not Found..."));
	}

	@ExceptionHandler(TimesheetExistsException.class)
	public ResponseEntity<Message> handleTimesheetExist(TimesheetExistsException ex) {
		return ResponseEntity.ok(new Message("Timesheet already exists.."));
	}

	@ExceptionHandler(LeaveExistException.class)
	public ResponseEntity<Message> handleLeaveExist(LeaveExistException ex) {
		return ResponseEntity.ok(new Message("Leave already exists.."));
	}

	

}
