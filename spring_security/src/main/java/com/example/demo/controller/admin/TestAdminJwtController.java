package com.example.demo.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.service.AdminService;
import com.example.demo.security.jwt.JwtTokenUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title       JWT 관련 발급 컨트롤러
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2022-01-04
 * @Description JWT 토큰 발급 컨트롤러
 */
@RestController
public class TestAdminJwtController {
	@Autowired AdminService adminService;
	@Autowired JwtTokenUtil jwtTokenUtil;
	
	// JWT 토큰 발급
	@PostMapping(value="admin/authentication")
	public ResponseEntity<?> TestAdminAdminGet(@RequestBody Map<String, Object> input, HttpServletRequest req) throws Exception{
		AdminDTO aDTO = adminService.loadAdminByAdminId(input.get("adminId").toString(), input.get("pw").toString());
		
		final String token = jwtTokenUtil.generateToken(aDTO.getAdmId());
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@Data
	class JwtRequest {
	private String email;
	private String password;
	}
	
	@Data
	@AllArgsConstructor
	class JwtResponse {
	private String token;
	}
}
