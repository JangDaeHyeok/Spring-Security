package com.example.demo.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.service.AdminService;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * @Title       JWT Request Filter
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2022-01-04
 * @Description JWT을 검증하는 filter
 *              OncePerRequestFilter를 상속해 요청당 한번의 filter를 수행한다.
 *              EXCLUDE_URL에 있는 url은 체크하지 않는다.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired private AdminService adminService;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	
	// 포함하지 않을 url
	private static final List<String> EXCLUDE_URL =
		Collections.unmodifiableList(
			Arrays.asList(
				"/static/**",
				"/favicon.ico",
				"/admin",
				"/admin/authentication",
				"/admin/join",
				"/admin/join/**",
				"/admin/loginView",
				"/admin/login"
			));
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String adminId = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				adminId = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		
		if(adminId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			AdminDTO adminDTO = new AdminDTO();
			try {
				adminDTO = adminService.loadAdminByAdminId(adminId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(jwtTokenUtil.validateToken(jwtToken, adminDTO)) {
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(adminDTO, null ,adminDTO.getAuthorities());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request,response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
	}
}
