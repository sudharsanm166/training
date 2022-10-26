
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
import java.util.Optional;

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

import com.demo.spring.controllers.AdminEmpRestController;
import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Timesheet;
import com.demo.spring.repository.EmpRepository;
import com.demo.spring.repository.LeaveRepository;
import com.demo.spring.repository.TimesheetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
public class AdminRestControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	EmpRepository emprepo;

	@MockBean
	TimesheetRepository timerepo;

	@MockBean
	LeaveRepository leaverepo;

	@InjectMocks
	AdminEmpRestController erc;

	// add emp 

	@Test
	void testAddEmp() throws Exception {
		Employee e = new Employee(100, "hari", "noida", 45000);
		when(emprepo.save(e)).thenReturn(e);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(post("/Admin/add/emp").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// view emp

	@Test 
	void testFindById() throws Exception {

		Mockito.when(emprepo.findById(104)).thenReturn(Optional.of(new Employee(104, "hari", "noida", 47820)));

		mvc.perform(get("/Admin/emp/104").accept(MediaType.APPLICATION_JSON_VALUE))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(jsonPath("$.name").value("hari"));
	}

	// list all emp

	@Test
	void testFindAllEmp() throws Exception {

		List<Employee> emp = new ArrayList<>();
		emp.add(new Employee(100, "Akanksha", "Bokaro", 54000));

		Mockito.when(emprepo.findAll()).thenReturn(emp);

		mvc.perform(get("/Admin/emplist").accept(MediaType.APPLICATION_JSON_VALUE))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print());
	}

	// add timesheet

	@Test
	void testSaveTimesheet() throws Exception {
		Timesheet t = new Timesheet(100, 10001, "Project", "Bokaro", 40);
		when(timerepo.save(t)).thenReturn(t);

		ObjectMapper om = new ObjectMapper();
		String timeJson = om.writeValueAsString(t);

		mvc.perform(post("/Admin/add/time").contentType(MediaType.APPLICATION_JSON).content(timeJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// view timesheet record

	@Test
	void testFindallTimesheet() throws Exception {

		List<Timesheet> time = new ArrayList<>();
		time.add(new Timesheet(100, 10001, "Project", "noida", 40));

		Mockito.when(timerepo.findAllByEmpId(100)).thenReturn(time);

		mvc.perform(get("/Admin/time/100").accept(MediaType.APPLICATION_JSON_VALUE))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(jsonPath("$.[0].empId").value("100"));
	}

	// view timesheet list

	@Test
	void testFindAllTimesheet() throws Exception {

		List<Timesheet> time = new ArrayList<>();
		time.add(new Timesheet(100, 10001, "Project", "noida", 40));

		Mockito.when(timerepo.findAll()).thenReturn(time);

		mvc.perform(get("/Admin/timelist").accept(MediaType.APPLICATION_JSON_VALUE))
				// .andDo(print())
				.andExpectAll(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print());
	}

	// apply leave

	@Test
	void testApplyLeave() throws Exception {
		Leave l = new Leave(100, 10001, 2, "Sick", "Approved");
		when(leaverepo.save(l)).thenReturn(l);

		ObjectMapper om = new ObjectMapper();
		String leaveJson = om.writeValueAsString(l);

		mvc.perform(post("/Admin/add/leave").contentType(MediaType.APPLICATION_JSON).content(leaveJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// approve leave

	@Test
	void testUpdateEmp() throws Exception {
		Leave l = new Leave(100, 10001, 2, "Sick", "Applied");
		when(leaverepo.save(l)).thenReturn(l);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(l);

		mvc.perform(put("/Admin/approve/leave").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// view leaves record

	@Test
	void testFindAllLeaves() throws Exception {

		List<Leave> leave = new ArrayList<>();
		leave.add(new Leave(100, 10001, 2, "Sick", "Applied"));

		Mockito.when(leaverepo.findAll()).thenReturn(leave);

		mvc.perform(get("/Admin/leavelist").accept(MediaType.APPLICATION_JSON_VALUE))
				// .andDo(print())
				.andExpectAll(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print());
	}

	// ----negative test cases-------- 
	
	//add emp details

	@Test
	void testAddEmpDetailsNegative() throws Exception {
		Employee e = new Employee(109, "ram", "Noida", 45000);
		when(emprepo.existsById(109)).thenReturn(true);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(post("/Admin/add/emp").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Employee exists..."));
	}

	// add timesheet

	@Test
	void testSaveNegative() throws Exception {
		Timesheet t = new Timesheet(106, 1005, "Tech-Project", "Noida", 40);
		when(timerepo.existsById(1005)).thenReturn(true);

		ObjectMapper om = new ObjectMapper();
		String timeJson = om.writeValueAsString(t);

		mvc.perform(post("/Admin/add/time").contentType(MediaType.APPLICATION_JSON).content(timeJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Timesheet Exists.."));
	}

	// apply leave

	@Test
	void testApplyLeaveNegative() throws Exception {
		Leave l = new Leave(111, 10001, 2, "sick", "Applied");
		when(leaverepo.existsById(10001)).thenReturn(true);

		ObjectMapper om = new ObjectMapper();
		String leaveJson = om.writeValueAsString(l);

		mvc.perform(post("/Admin/add/leave").contentType(MediaType.APPLICATION_JSON).content(leaveJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Leave Id already exists.."));

	}

	// edit leave

	@Test
	void testUpdateEmpNegative() throws Exception {
		Leave e = new Leave(100, 10001, 1, "casual", "applied");
		when(leaverepo.existsById(10001)).thenReturn(false);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(put("/Admin/approve/leave").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Leave Id Not exists.."));

	}

}
