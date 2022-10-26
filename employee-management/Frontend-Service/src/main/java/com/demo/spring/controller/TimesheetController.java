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

import com.demo.spring.entity.Timesheet;

@Controller
@RequestMapping("/Ui")
public class TimesheetController {

	@Autowired
	RestTemplate rt;
	
	@GetMapping(path="findtime")
	public String getTimePage() {
		return "find-timesheet";
	}

	@PostMapping(path = "findtime")
	public ModelAndView getTimesheetDetails(@RequestParam("id") int id) {
		Timesheet time=rt.getForObject("http://gateway-services/Admin/time/" + id, Timesheet.class);
		ModelAndView mv= new ModelAndView();
		mv.addObject("timeData", time);
		mv.setViewName("find-timesheet");
		return mv;
	}
	
	@GetMapping(path = "findOneTime")
	public ModelAndView findOneTime(@RequestParam("id") int id) {
		Timesheet time=rt.getForObject("http://gateway-services/Admin/time/" + id, Timesheet.class);
		ModelAndView mv= new ModelAndView();
		mv.addObject("timeData", time);
		mv.setViewName("find-timesheet");
		return mv;
	}
	
	@GetMapping(path = "listtime")
	public ModelAndView listAll() {
		ResponseEntity<List<Timesheet>> timeList=
				rt.exchange("http://gateway-services/Admin/timelist/",HttpMethod.GET,null, new ParameterizedTypeReference<List<Timesheet>>() {
		});
		ModelAndView mv= new ModelAndView();
		mv.addObject("timelist", timeList.getBody());
		mv.setViewName("timelist");
		return mv;
	}
	
	@GetMapping(path="addTime")
	public String getAddPage(ModelMap map) {
		Timesheet time= new Timesheet();
		map.addAttribute("emp", time);
		return "add-time";
	}
	
	@PostMapping(path="saveTime")
	public ModelAndView saveEmp(@ModelAttribute("emp") Timesheet t) {
		ModelAndView mv= new ModelAndView();
		HttpEntity req=new HttpEntity(t);
		ResponseEntity<String> resp= rt.exchange("http://gateway-services/Admin/add/time/",HttpMethod.POST,req, String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	@GetMapping(path="editTime")
	public String getUpdatePage(ModelMap map) {
		Timesheet time= new Timesheet();
		map.addAttribute("emp", time);
		return "edit-time";
	}
	
	@PostMapping(path="editTime")
	public ModelAndView updateTimesheet(@ModelAttribute("emp") Timesheet e) {
		ModelAndView mv= new ModelAndView();
		HttpEntity req=new HttpEntity(e);
		ResponseEntity<String> resp= rt.exchange("http://gateway-services/Admin/edit/time/",HttpMethod.PUT,req, String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
}