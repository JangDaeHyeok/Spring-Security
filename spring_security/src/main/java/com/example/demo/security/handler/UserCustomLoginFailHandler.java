package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @Title       Spring Security AuthenticationProvider를 통한 사용자 로그인 인증이 실패할 경우 처리
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description 사용자 로그인 인증이 실패할 경우 로그인 페이지로 반환
 */
public class UserCustomLoginFailHandler implements AuthenticationFailureHandler {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.info("*********************************************************************");
		log.info("[UserCustomLoginFailHandler] 로그인 실패..");
		log.info("*********************************************************************");
		
		response.sendRedirect("/user/loginView");
		
	}
}
