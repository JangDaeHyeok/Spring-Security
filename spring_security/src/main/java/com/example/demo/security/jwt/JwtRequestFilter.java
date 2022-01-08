package com.example.demo.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.service.AdminService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

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
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private AdminService adminService;
	@Autowired private JwtTokenProvider jwtTokenUtil;
	
	// 포함하지 않을 url
	private static final List<String> EXCLUDE_URL =
		Collections.unmodifiableList(
			Arrays.asList(
				"/static/**",
				"/favicon.ico",
				"/admin",
				"/admin/authentication",
				"/admin/refresh",
				"/admin/join",
				"/admin/join/**",
				"/admin/loginView",
				"/admin/login"
			));
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// jwt local storage 사용 시
		// final String token = request.getHeader("Authorization");
		
		// jwt cookie 사용 시
		String token = Arrays.stream(request.getCookies())
				.filter(c -> c.getName().equals("jdhToken"))
				.findFirst() .map(Cookie::getValue)
				.orElse(null);
		
		String adminId = null;
		String jwtToken = null;
		
		// Bearer token인 경우
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);
			try {
				adminId = jwtTokenUtil.getUsernameFromToken(jwtToken);
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
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		
		// token 검증이 되고 인증 정보가 존재하지 않는 경우 spring security 인증 정보 저장
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
