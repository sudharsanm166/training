package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.entity.Payslip;

@Controller
@RequestMapping("/Ui")
public class PayslipController {

	@Autowired
	RestTemplate rt;
	
	@GetMapping(path="findpay")
	public String getPayPage() {
		return "find-payslip";
	}

	@PostMapping(path = "/findpay")
	public ModelAndView getEmpDetails(@RequestParam("id") int id) {
		Payslip pay=rt.getForObject("http://gateway-services/Employee/pays/list/" + id, Payslip.class);
		ModelAndView mv= new ModelAndView();
		mv.addObject("empData", pay);
		mv.setViewName("find-payslip");
		return mv;
	}

	
}
