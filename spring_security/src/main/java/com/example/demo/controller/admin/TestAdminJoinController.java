package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.dto.AdminRoleRlDTO;
import com.example.demo.model.dto.RoleDTO;
import com.example.demo.model.service.AdminRoleRlService;
import com.example.demo.model.service.AdminService;
import com.example.demo.model.service.RoleService;

@RestController
public class TestAdminJoinController {
	@Autowired BCryptPasswordEncoder passwordEncoder;
	
	@Autowired AdminService adminService;
	@Autowired AdminRoleRlService adminRoleRlService;
	@Autowired RoleService roleService;
	
	// 회원가입 실행
	@RequestMapping(value="admin/join/mgmt", method = RequestMethod.POST)
	public Map<String, Object> TestAdminJoinAdd(@RequestBody Map<String, Object> input, HttpServletRequest req) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		// 회원 정보 저장
		AdminDTO aDTO = new AdminDTO();
		aDTO.setAdmIdx(UUID.randomUUID().toString().replace("-",""));
		aDTO.setAdmId(input.get("adminId").toString());
		aDTO.setAdmPw(passwordEncoder.encode(input.get("adminPw").toString()));
		
		int result = adminService.addAdmin(aDTO);
		
		if(result > 0) {
			returnMap.put("result", "success");
			returnMap.put("msg", "회원가입 성공");
		}else {
			returnMap.put("result", "fail");
			returnMap.put("msg", "회원가입 실패");
		}
		
		// ADMIN ROLE 저장
		RoleDTO rDTO = new RoleDTO();
		rDTO.setRoleNm("ROLE_ADMIN");
		RoleDTO roleDTO = roleService.getRole(rDTO);
		
		AdminRoleRlDTO arDTO = new AdminRoleRlDTO();
		arDTO.setAdmRoleRlIdx(UUID.randomUUID().toString().replace("-",""));
		arDTO.setRoleIdx(roleDTO.getRoleIdx());
		arDTO.setAdmIdx(aDTO.getAdmIdx());
		int result2 = adminRoleRlService.addAdminRole(arDTO);
		
		if(result2 > 0) {
			returnMap.put("result2", "success");
			returnMap.put("msg2", "ADMIN ROLE 저장 성공");
		}else {
			returnMap.put("result2", "fail");
			returnMap.put("msg2", "ADMIN ROLE 저장 실패");
			return returnMap;
		}
		
		return returnMap;
	}
}
