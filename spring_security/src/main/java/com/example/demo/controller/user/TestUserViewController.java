package com.example.demo.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestUserViewController {
	@RequestMapping(value="/")
	public ModelAndView TestIndex() {
		ModelAndView mv = new ModelAndView("user/index");
		return mv;
	}
	
	@RequestMapping(value="about")
	public ModelAndView TestAbout() {
		ModelAndView mv = new ModelAndView("user/about");
		return mv;
	}
	
	@RequestMapping(value="index")
	public ModelAndView TestIndex2() {
		ModelAndView mv = new ModelAndView("user/index");
		return mv;
	}
	
	@RequestMapping(value="hello")
	public ModelAndView TestHello() {
		ModelAndView mv = new ModelAndView("user/hello");
		return mv;
	}
	
	@RequestMapping(value="loginCheck")
	public ModelAndView TestLogin() {
		ModelAndView mv = new ModelAndView("user/login");
		return mv;
	}
}
