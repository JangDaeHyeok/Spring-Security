package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.AdminRoleRlDTO;
import com.example.demo.model.service.AdminRoleRlService;

@RestController
public class TestAdminAdminRoleController {
	@Autowired AdminRoleRlService adminRoleRlService;
	
	@RequestMapping(value="admin/admin/role/mgmt", method = RequestMethod.PATCH)
	public Map<String, Object> TestAdminAdminRoleChg(@RequestBody Map<String, Object> input, HttpServletRequest req) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		AdminRoleRlDTO arDTO = new AdminRoleRlDTO();
		arDTO.setAdmIdx(input.get("admIdx").toString());
		arDTO.setRoleIdx(input.get("roleIdx").toString());
		
		int result = adminRoleRlService.chgAdminRoleByAdmIdx(arDTO);
		
		/**
		 * @Title       Spring Security 권한 교체
		 * @Author      장대혁
		 * @Developer   장대혁
		 * @Date        2021-11-11
		 * @Description 수정된 권한으로 교체한다.
		 * @Warning     현재 접속한 사용자 기준 인증정보 교체이기 때문에 필요 시에만 사용한다.
		 
		// 권한이름 조회
		String roleNm = input.get("roleNm").toString();
		
		// 기존 권한 조회
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		
		// 수정될 role 추가 (필요 시 여러개 add 가능)
		updatedAuthorities.add(new SimpleGrantedAuthority(roleNm));
		
		// 추가한 권한정보로 다시 Security가 관리할 수 있는 객체를 생성
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
		
		// Security가 관리하는 객체를 새로 만든 인증정보로 변경
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		// e :: 수정된 권한으로 스프링 시큐리티 권한 수정 :: //
		*/
		
		if(result > 0) {
			returnMap.put("result", "success");
			returnMap.put("msg", "관리자 권한 수정 성공");
		}else {
			returnMap.put("result", "fail");
			returnMap.put("msg", "관리자 권한 수정 실패");
			return returnMap;
		}
		return returnMap;
	}
}
