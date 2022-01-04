package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @Title       Spring Security JWT 인정 정보가 존재하지 않는 경우 실패 처리
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2022-01-04
 * @Description Spring Security JWT 인정 정보가 존재하지 않는 경우 401 에러 반환
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint  {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
	}

}
