
package com.demo.spring;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.controllers.EmpRestController;
import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Payslip;
import com.demo.spring.entity.Timesheet;
import com.demo.spring.repository.EmpRepository;
import com.demo.spring.repository.LeaveRepository;
import com.demo.spring.repository.PayslipRepository;
import com.demo.spring.repository.TimesheetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
class EmpRestControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	EmpRepository emprepo;

	@MockBean
	TimesheetRepository timerepo;

	@MockBean
	LeaveRepository leaverepo;

	@MockBean
	PayslipRepository payrepo;

	@InjectMocks
	EmpRestController erc;

	// edit profile

	@Test
	void testUpdateEmp() throws Exception {
		Employee e = new Employee(100, "sai", "noida", 45000);
		when(emprepo.save(e)).thenReturn(e);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(put("/Employee/edit/emp").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// add timesheet

	@Test
	void testSaveTimesheet() throws Exception {
		Timesheet t = new Timesheet(100, 10001, "Project", "noida", 40);
		when(timerepo.save(t)).thenReturn(t);

		ObjectMapper om = new ObjectMapper();
		String timeJson = om.writeValueAsString(t);

		mvc.perform(post("/Employee/add/time").contentType(MediaType.APPLICATION_JSON).content(timeJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// view timesheet record

	@Test
	void testFindAllTimesheet() throws Exception {

		List<Timesheet> time = new ArrayList<>();
		time.add(new Timesheet(100, 10001, "Project", "noida", 40));

		Mockito.when(timerepo.findAllByEmpId(100)).thenReturn(time);

		mvc.perform(get("/Employee/time/list/100").accept(MediaType.APPLICATION_JSON_VALUE)) // .andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(jsonPath("$.[0].empId").value("100"));
	}

	// view payslip record

	@Test
	void testFindAllPayslip() throws Exception {

		List<Payslip> pay = new ArrayList<>();
		pay.add(new Payslip(101, 10001, "June", 30, 40000));

		Mockito.when(payrepo.findAllByEmpId(101)).thenReturn(pay);

		mvc.perform(get("/Employee/pays/list/101").accept(MediaType.APPLICATION_JSON_VALUE)) // .andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(jsonPath("$.[0].empId").value("101"));
	}

	// apply leave

	@Test
	void testApplyLeave() throws Exception {
		Leave l = new Leave(100, 10001, 2, "Sick", "Applied");
		when(leaverepo.save(l)).thenReturn(l);

		ObjectMapper om = new ObjectMapper();
		String leaveJson = om.writeValueAsString(l);

		mvc.perform(post("/Employee/add/leave").contentType(MediaType.APPLICATION_JSON).content(leaveJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// view leaves record

	@Test
	void testFindAllLeaves() throws Exception {

		List<Leave> leave = new ArrayList<>();
		leave.add(new Leave(100, 10001, 3, "Family function", "Applied"));

		Mockito.when(leaverepo.findAllByEmpId(100)).thenReturn(leave);

		mvc.perform(get("/Employee/leave/100").accept(MediaType.APPLICATION_JSON_VALUE)) // .andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(jsonPath("$.[0].empId").value("100"));
	}

	// ----negative test cases--------
	
	//edit emp details

	@Test
	void testEditEmpDetailsNegative() throws Exception {
		Employee e = new Employee(109, "Hari", "Noida", 45000);
		when(emprepo.existsById(109)).thenReturn(false);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(put("/Employee/edit/emp").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Employee Not Found..."));
	}

	// add timesheet

	@Test
	void testSaveNegative() throws Exception {
		Timesheet t = new Timesheet(106, 1005, "Tech-Project", "Noida", 40);
		when(timerepo.existsById(1005)).thenReturn(true);

		ObjectMapper om = new ObjectMapper();
		String timeJson = om.writeValueAsString(t);

		mvc.perform(post("/Employee/add/time").contentType(MediaType.APPLICATION_JSON).content(timeJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Timesheet already exists.."));
	}

	// apply leave

	@Test
	void testApplyLeaveNegative() throws Exception {
		Leave l = new Leave(111, 10001, 2, "Marriage", "Applied");
		when(leaverepo.existsById(10001)).thenReturn(true);

		ObjectMapper om = new ObjectMapper();
		String leaveJson = om.writeValueAsString(l);

		mvc.perform(post("/Employee/add/leave").contentType(MediaType.APPLICATION_JSON).content(leaveJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Leave already exists.."));

	}

	@Test
	void testUpdateEmployeeNeg() throws Exception {
		Employee e = new Employee(109, "Puja", "Noida", 45000);
		when(emprepo.save(e)).thenReturn(e);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(put("/Employee/edit/emp").contentType(MediaType.APPLICATION_JSON).content(value)) // .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Employee Not Found..."));
	}

}
