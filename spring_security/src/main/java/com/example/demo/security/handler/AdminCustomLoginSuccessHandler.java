package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @Title       Spring Security AuthenticationProvider를 통해 인증이 성공될 경우 처리
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description 성공하여 반환된 Authentication 객체를 SecurityContextHolder의 Contetx에 저장하고, /about 으로 redirect
 */
public class AdminCustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		log.info("*********************************************************************");
		log.info("[AdminCustomLoginSuccessHandler] 로그인 성공!!");
		log.info("*********************************************************************");
		
		response.sendRedirect("/admin");
	}
}
