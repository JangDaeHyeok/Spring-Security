package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @Title       Spring Security WebAccessDeniedHandler
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description 서버에 요청을 할 때 액세스가 가능한지 권한을 체크 후 액세스 할 수 없는 요청을 했을시 동작
 */
@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.warn("[WebAccessDeniedHandler] 권한이 없는 사용자 접근");
		response.sendRedirect("/error/error403");
		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
