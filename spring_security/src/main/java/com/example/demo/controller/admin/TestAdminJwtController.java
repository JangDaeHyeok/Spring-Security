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
import com.example.demo.model.dto.RefreshTokenDTO;
import com.example.demo.model.service.AdminService;
import com.example.demo.model.service.RefreshTokenService;
import com.example.demo.security.jwt.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
	@Autowired RefreshTokenService refreshTokenService;
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
	public Map<String, Object> testJwtTokenGet(@RequestBody Map<String, Object> input, HttpServletRequest req, HttpServletResponse rep) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		AdminDTO aDTO = adminService.loadAdminByAdminId(input.get("adminId").toString(), input.get("adminPw").toString());
		
		// 권한 map 저장
		Map<String, Object> rules = new HashMap<String, Object>();
		rules.put("rules", adminService.loadAdminAuthArrayByAdminId(aDTO.getAdmId()));
		// JWT 발급
		Map<String, String> tokens = jwtTokenUtil.generateTokenSet(aDTO.getAdmId(), rules);
		String accessToken = URLEncoder.encode(tokens.get("accessToken"), "utf-8");
		String refreshToken = URLEncoder.encode(tokens.get("refreshToken"), "utf-8");
		
		log.info("[JWT 발급] accessToken : " + accessToken);
		log.info("[JWT 발급] refreshToken : " + refreshToken);
		
		// JWT 쿠키 저장(쿠키 명 : token)
		Cookie cookie = new Cookie("jdhToken", "Bearer " + accessToken);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 1); // 유효기간 1일
		// httoOnly 옵션을 추가해 서버만 쿠키에 접근할 수 있게 설정
		cookie.setHttpOnly(true);
		rep.addCookie(cookie);
		
		// 비밀번호 정보 제거
		aDTO.setAdmPw("");
		
		// refresh token 정보 저장/수정
		RefreshTokenDTO rDTO = new RefreshTokenDTO();
		rDTO.setAdmIdx(aDTO.getAdmIdx());
		rDTO.setRefreshToken("Bearer " + refreshToken);
		refreshTokenService.addRefreshToken(rDTO);
		
		returnMap.put("result", "success");
		returnMap.put("msg", "JWT가 발급되었습니다.");
		return returnMap;
	}
	
	// JWT 토큰 재발급
	@PostMapping(value="admin/refresh")
	public Map<String, Object> testJwtTokenRefresh(@RequestBody Map<String, Object> input, HttpServletRequest req, HttpServletResponse rep) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String refreshToken = null;
		String adminId = "";
		
		// 관리자 정보 조회
		AdminDTO aDTO = adminService.loadAdminByAdminId(input.get("adminId").toString());
		
		// refreshToken 정보 조회
		RefreshTokenDTO rDTO = new RefreshTokenDTO();
		rDTO.setAdmIdx(aDTO.getAdmIdx());
		rDTO = refreshTokenService.getRefreshToken(rDTO);
		
		// token 정보가 존재하지 않는 경우
		if(rDTO == null) {
			returnMap.put("result", "fail");
			returnMap.put("msg", "refresh token 정보가 존재하지 않습니다.");
			return returnMap;
		}
		// token 정보가 존재하는 경우
		else {
			refreshToken = rDTO.getRefreshToken();
		}
		
		// refreshToken 인증
		boolean tokenFl = false;
		try {
			refreshToken = refreshToken.substring(7);
			adminId = jwtTokenUtil.getUsernameFromToken(refreshToken);
			tokenFl = true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		
		// refreshToken 사용이 불가능한 경우
		if(!tokenFl) {
			returnMap.put("result", "fail");
			returnMap.put("msg", "refresh token이 만료되었거나 정보가 존재하지 않습니다.");
			
			// refreshToken 정보 조회 실패 시 기존에 존재하는 refreshToken 정보 삭제
			refreshTokenService.delRefreshToken(rDTO.getAdmRefreshTokenIdx());
			
			return returnMap;
		}
		
		// refreshToken 인증 성공인 경우 accessToken 재발급
		if(adminId != null && !adminId.equals("")) {
			// 권한 map 저장
			Map<String, Object> rules = new HashMap<String, Object>();
			rules.put("rules", adminService.loadAdminAuthArrayByAdminId(input.get("adminId").toString()));
			
			// JWT 발급
			String tokens = jwtTokenUtil.generateAccessToken(input.get("adminId").toString(), rules);
			String accessToken = URLEncoder.encode(tokens, "utf-8");
			
			log.info("[JWT 재발급] accessToken : " + accessToken);
			
			// JWT 쿠키 저장(쿠키 명 : token)
			Cookie cookie = new Cookie("jdhToken", "Bearer " + accessToken);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 1); // 유효기간 1일
			// httoOnly 옵션을 추가해 서버만 쿠키에 접근할 수 있게 설정
			cookie.setHttpOnly(true);
			rep.addCookie(cookie);
			
			returnMap.put("result", "success");
			returnMap.put("msg", "JWT가 발급되었습니다.");
		}else {
			returnMap.put("result", "fail");
			returnMap.put("msg", "access token 발급 중 문제가 발생했습니다.");
			return returnMap;
		}
		
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
