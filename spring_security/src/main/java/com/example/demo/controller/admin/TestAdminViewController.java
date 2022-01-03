package com.example.demo.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestAdminViewController {
	@RequestMapping(value="admin")
	public ModelAndView TestAdminHome() {
		ModelAndView mv = new ModelAndView("admin/hello");
		
		return mv;
	}
	
	@RequestMapping(value="admin/admin")
	public ModelAndView TestAdminAbout() {
		ModelAndView mv = new ModelAndView("admin/admin");
		return mv;
	}
	
	@RequestMapping(value="admin/index")
	public ModelAndView TestAdminIndex() {
		ModelAndView mv = new ModelAndView("admin/index");
		return mv;
	}
	
	@RequestMapping(value="admin/hello")
	public ModelAndView TestAdminHello() {
		ModelAndView mv = new ModelAndView("admin/hello");
		return mv;
	}
}
