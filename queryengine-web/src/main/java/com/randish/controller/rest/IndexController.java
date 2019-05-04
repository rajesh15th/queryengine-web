package com.randish.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping("/")
	public String homePage(ModelAndView mv) {
		System.out.println("in home page");
		return "index";
	}
	@GetMapping("/databaseInfo")
	public String databaseInfo(ModelAndView mv) {
		System.out.println("databaseInfo");
		return "databaseInfo";
	}

}