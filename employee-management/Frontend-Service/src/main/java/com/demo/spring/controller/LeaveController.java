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

import com.demo.spring.entity.Leave;

@Controller
@RequestMapping("/Ui")
public class LeaveController {
	@Autowired
	RestTemplate rt;
	
	@GetMapping(path="findleave")
	public String getLeavesPage() {
		return "find-leave";
	}

	@PostMapping(path = "findleave")
	public ModelAndView getLeaveDetails(@RequestParam("id") int id) {
		Leave leave=rt.getForObject("http://admin-service/Employee/leave/" + id, Leave.class);
		ModelAndView mv= new ModelAndView();
		mv.addObject("leaveData", leave);
		mv.setViewName("find-leave");
		return mv;
	}
	
	@GetMapping(path = "findOneLeave")
	public ModelAndView findOneLeave(@RequestParam("id") int id) {
		Leave leave=rt.getForObject("http://gateway-services/Employee/leave/" + id, Leave.class);
		ModelAndView mv= new ModelAndView();
		mv.addObject("leaveData", leave);
		mv.setViewName("find-leave");
		return mv;
	}
	
	@GetMapping(path = "listleave")
	public ModelAndView listAll() {
		ResponseEntity<List<Leave>> leaveList=
				rt.exchange("http://admin-service/Admin/leavelist/",HttpMethod.GET,null, new ParameterizedTypeReference<List<Leave>>() {
		});
		ModelAndView mv= new ModelAndView();
		mv.addObject("leavelist", leaveList.getBody());
		mv.setViewName("leavelist");
		return mv;
	}

	@GetMapping(path="addleave")
	public String getAddPage(ModelMap map) {
		Leave leave= new Leave();
		map.addAttribute("leave", leave);
		return "add-leave";
	}
	
	@PostMapping(path="saveleave")
	public ModelAndView saveLeaves(@ModelAttribute("leave") Leave e) {
		ModelAndView mv= new ModelAndView();
		HttpEntity<Leave> req=new HttpEntity<>(e);
		ResponseEntity<String> resp= rt.exchange("http://gateway-services/Admin/add/leave/",HttpMethod.POST,req, String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	

	@GetMapping(path="approveleave")
	public String getUpdatePage(ModelMap map) {
		Leave leave= new Leave();
		map.addAttribute("emp", leave);
		return "edit-leave";
	}
	
	@PostMapping(path="approveleave")
	public ModelAndView updateLeave(@ModelAttribute("emp") Leave l) {
		ModelAndView mv= new ModelAndView();
		HttpEntity req=new HttpEntity(l);
		ResponseEntity<String> resp= rt.exchange("http://gateway-services/Admin/approve/leave",HttpMethod.PUT,req, String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}

}
