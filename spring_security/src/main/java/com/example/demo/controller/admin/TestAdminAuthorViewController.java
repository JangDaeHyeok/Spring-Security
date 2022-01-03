package com.example.demo.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestAdminAuthorViewController {
	@RequestMapping(value="admin/author")
	public ModelAndView TestAdminAuthor() {
		ModelAndView mv = new ModelAndView("admin/author");
		return mv;
	}
}
