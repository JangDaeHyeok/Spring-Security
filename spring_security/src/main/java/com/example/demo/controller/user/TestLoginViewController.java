package com.example.demo.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestLoginViewController {
	
	@RequestMapping(value="user/loginView")
	public ModelAndView TestLoginView() {
		ModelAndView mv = new ModelAndView("user/loginView");
		return mv;
	}
	
	@RequestMapping(value="user/login")
	public ModelAndView TestLogin() {
		ModelAndView mv = new ModelAndView("user/login");
		return mv;
	}
	
	// 회원가입 페이지
	@RequestMapping(value="user/join")
	public ModelAndView TestJoin() {
		ModelAndView mv = new ModelAndView("user/join");
		return mv;
	}
}
