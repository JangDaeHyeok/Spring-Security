package com.example.demo.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestAdminRoleViewController {
	@RequestMapping(value="admin/adminRole")
	public ModelAndView TestAdminAuthor() {
		ModelAndView mv = new ModelAndView("admin/adminRole");
		return mv;
	}
}
