package com.example.demo.controller.admin;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.service.AdminService;
import com.example.demo.security.jwt.JwtTokenProvider;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title       JWT 관련 발급 컨트롤러
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2022-01-04
 * @Description JWT 토큰 발급 컨트롤러, 쿠키 저장
 */
@RestController
public class TestAdminJwtController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired AdminService adminService;
	@Autowired JwtTokenProvider jwtTokenUtil;
	
	// JWT 토큰 발급
	/* localStorage 사용 시
	@PostMapping(value="admin/authentication")
	public ResponseEntity<AdminDTO> TestAdminAdminGet(@RequestBody Map<String, Object> input, HttpServletRequest req, HttpServletResponse rep) throws Exception{
		AdminDTO aDTO = adminService.loadAdminByAdminId(input.get("adminId").toString(), input.get("adminPw").toString());
		
		// JWT 발급
		final String token = jwtTokenUtil.generateToken(aDTO.getAdmId());
		aDTO.setToken(token);
		
		// 비밀번호 정보 제거
		aDTO.setAdmPw("");
		
		return ResponseEntity.ok(aDTO);
	}
	*/
	
	// JWT 토큰 발급(쿠키 사용 시)
	@PostMapping(value="admin/authentication")
	public Map<String, Object> TestAdminAdminGet(@RequestBody Map<String, Object> input, HttpServletRequest req, HttpServletResponse rep) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		AdminDTO aDTO = adminService.loadAdminByAdminId(input.get("adminId").toString(), input.get("adminPw").toString());
		
		// JWT 발급
		String token = jwtTokenUtil.generateToken(aDTO.getAdmId());
		token = URLEncoder.encode(token, "utf-8");
		
		log.info("[JWT 발급] token : " + token);
		
		// JWT 쿠키 저장(쿠키 명 : token)
		Cookie cookie = new Cookie("jdhToken", "Bearer " + token);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 1); // 유효기간 1일
		// httoOnly 옵션을 추가해 서버만 쿠키에 접근할 수 있게 설정
		cookie.setHttpOnly(true);
		rep.addCookie(cookie);
		
		// 비밀번호 정보 제거
		aDTO.setAdmPw("");
		
		returnMap.put("result", "success");
		returnMap.put("msg", "JWT가 발급되었습니다.");
		return returnMap;
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
