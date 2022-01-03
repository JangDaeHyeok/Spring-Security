package com.example.demo.security.checker;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.service.AdminService;
import com.example.demo.model.staticval.AdmMenuStaticValue;
import com.example.demo.model.vo.SecurityUrlMatcher;

/**
 * @Title       Spring Security Admin Authorization Checker
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-16
 * @Description 관리자 권한을 페이지 이동 시 권한이 있는 url인지 체크하는 로직
 * @Warning     db url 정보가 변경되는 경우 AdmMenuStaticValue.admMenuList를 수정해줘야 한다.
 */
@Component
public class AuthorizationChecker {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private AdminService adminService;
	
	@Cacheable("check")
	public boolean check(HttpServletRequest request, Authentication authentication) throws Exception{
		Object principalObj = authentication.getPrincipal();
		
		if (!(principalObj instanceof AdminDTO)) {
			return false;
		}
		
		List<String> authority = new ArrayList<String>();
		// Initializer에서 저장한 admin menu static list를 이용한 메뉴-권한 조회
		for (SecurityUrlMatcher matcher : AdmMenuStaticValue.admMenuList) {
			log.info("[AuthorizationChecker] matcher url ==> " + matcher.getUrl() + ", ajax url ==> " + matcher.getAjaxUrl() + ", matcher authority ==> " + matcher.getAuthority());
			log.info("[AuthorizationChecker] request url ==> " + request.getRequestURI());
			if("true".equals(request.getHeader("AJAX"))) {
				if (matcher.getAjaxUrl() != null && new AntPathMatcher().match(matcher.getAjaxUrl(), request.getRequestURI())) {
					authority.add(matcher.getAuthority());
				}
			}else {
				if (new AntPathMatcher().match(matcher.getUrl(), request.getRequestURI())) {
					authority.add(matcher.getAuthority());
				}
			}
		}
		
		String adminId = ((AdminDTO) authentication.getPrincipal()).getAdmId();
		AdminDTO aDTO = new AdminDTO();
		aDTO.setAdmId(adminId);
		List<AdminDTO> authorities = adminService.loadAdminAuthByAdminId(adminId);
		if(authorities == null || authorities.size() == 0) {
			return false;
		}
		
		log.info("[AuthorizationChecker] admin authority ==> " + authority);
		log.info("[AuthorizationChecker] admin authorities ==> " + authorities);
		for(AdminDTO auth : authorities) {
			if (authority == null || !authority.contains(auth.getRoleNm())) {
				return false;
			}
		}
		
		return true;
	}
}