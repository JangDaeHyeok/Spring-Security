package com.example.demo.controller.error;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestErrorViewController {
	@RequestMapping(value="/error/error403")
	public ModelAndView TestIndex() {
		ModelAndView mv = new ModelAndView("error/error403");
		return mv;
	}
}
