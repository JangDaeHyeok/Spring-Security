package com.example.demo.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestAdminLoginViewController {
	
	@RequestMapping(value="admin/loginView")
	public ModelAndView TestAdminLoginView() {
		ModelAndView mv = new ModelAndView("admin/loginView");
		return mv;
	}
	
	@RequestMapping(value="admin/login")
	public ModelAndView TestAdminLogin() {
		ModelAndView mv = new ModelAndView("admin/jwtLogin");
		return mv;
	}
	
	// 회원가입 페이지
	@RequestMapping(value="admin/join")
	public ModelAndView TestAdminJoin() {
		ModelAndView mv = new ModelAndView("admin/join");
		return mv;
	}
	
}
