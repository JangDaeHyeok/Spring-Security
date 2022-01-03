package com.example.demo.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Title       Spring Security 사용자 인증 필터
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description 전송이 오면 AuthenticationFilter로 요청이 먼저 오게 되고, 아이디와 비밀번호를 기반으로 UserPasswordAuthenticationToken을 발급
 */
public class UsrCustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public UsrCustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getParameter("usrId"), request.getParameter("usrPw"));
		
		log.info("*********************************************************************");
		log.info("[UsrCustomAuthenticationFilter] UsernamePasswordAuthenticationToken 생성");
		log.info("usrId : " + request.getParameter("usrId").toString());
		log.info("usrPw : " + request.getParameter("usrPw").toString());
		log.info("*********************************************************************");
		
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
