package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.entity.Employee;

@Controller
@RequestMapping("/Ui")
public class EmpController {
	@Autowired
	RestTemplate rt;
	
	@GetMapping(path="find")
	public String getPage() {
		return "find-emp";
	}

	@PostMapping(path = "find")
	public ModelAndView getEmpDetails(@RequestParam("id") int id) {
		Employee emp=rt.getForObject("http://gateway-services/Admin/emp/" + id, Employee.class);
		ModelAndView mv= new ModelAndView();
		mv.addObject("empData", emp);
		mv.setViewName("find-emp");
		return mv;
	}
	
	@GetMapping(path = "findOne")
	public ModelAndView findOne(@RequestParam("id") int id) {
		Employee emp=rt.getForObject("http://gateway-services/Admin/emp/" + id, Employee.class);
		ModelAndView mv= new ModelAndView();
		mv.addObject("empData", emp);
		mv.setViewName("find-emp");
		return mv;
	}
	
	@GetMapping(path = "list")
	public ModelAndView listAll() {
		ResponseEntity<List<Employee>> empList=
				rt.exchange("http://gateway-services/Admin/emplist/",HttpMethod.GET,null, new ParameterizedTypeReference<List<Employee>>() {
		});
		ModelAndView mv= new ModelAndView();
		mv.addObject("emplist", empList.getBody());
		mv.setViewName("emplist");
		return mv;
	}

	@GetMapping(path="add")
	public String getAddPage(ModelMap map) {
		Employee emp= new Employee();
		map.addAttribute("emp", emp);
		return "add-emp";
	}
	
	@PostMapping(path="/save")
	public ModelAndView saveEmp(@ModelAttribute("emp") Employee e) {
		ModelAndView mv= new ModelAndView();
		HttpEntity<Employee> req=new HttpEntity<>(e);
		ResponseEntity<String> resp= rt.exchange("http://gateway-services/Admin/add/emp/",HttpMethod.POST,req, String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	@GetMapping(path="editEmp")
	public String getUpdatePage(ModelMap map) {
		Employee emp= new Employee();
		map.addAttribute("emp", emp);
		return "edit-emp";
	}
	
	@PostMapping(path="editEmp")
	public ModelAndView updateEmp(@ModelAttribute("emp") Employee e) {
		ModelAndView mv= new ModelAndView();
		HttpEntity<Employee> req=new HttpEntity<>(e);
		ResponseEntity<String> resp= rt.exchange("http://gateway-services/Employee/edit/emp/",HttpMethod.PUT,req, String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}

}
