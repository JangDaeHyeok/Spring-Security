package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.service.AdminService;

@RestController
public class TestAdminAdminController {
	@Autowired BCryptPasswordEncoder passwordEncoder;
	
	@Autowired AdminService adminService;
	
	// 회원가입 실행
	@RequestMapping(value="admin/admin/mgmt", method = RequestMethod.GET)
	public Map<String, Object> TestAdminAdminGet(@RequestParam Map<String, Object> input, HttpServletRequest req) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<AdminDTO> list = adminService.getAdmin(new AdminDTO());
		
		returnMap.put("list", list);
		returnMap.put("msg", "회원가입 성공");		
		return returnMap;
	}
}
