package com.example.demo.controller.user;

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

import com.example.demo.model.dto.RoleDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.dto.UserRoleRlDTO;
import com.example.demo.model.service.RoleService;
import com.example.demo.model.service.UserRoleRlService;
import com.example.demo.model.service.UserService;

@RestController
public class TestUserJoinController {
	@Autowired BCryptPasswordEncoder passwordEncoder;
	
	@Autowired UserService userService;
	@Autowired UserRoleRlService userRoleRlService;
	@Autowired RoleService roleService;
	
	// 회원가입 실행
	@RequestMapping(value="user/join/mgmt", method = RequestMethod.POST)
	public Map<String, Object> TestJoinAdd(@RequestBody Map<String, Object> input, HttpServletRequest req) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		// 회원 정보 저장
		UserDTO uDTO = new UserDTO();
		uDTO.setUsrIdx(UUID.randomUUID().toString().replace("-",""));
		uDTO.setUsrId(input.get("usrId").toString());
		uDTO.setUsrPw(passwordEncoder.encode(input.get("usrPw").toString()));
		
		int result = userService.addUser(uDTO);
		
		if(result > 0) {
			returnMap.put("result", "success");
			returnMap.put("msg", "회원가입 성공");
		}else {
			returnMap.put("result", "fail");
			returnMap.put("msg", "회원가입 실패");
			return returnMap;
		}
		
		// USER ROLE 저장
		RoleDTO rDTO = new RoleDTO();
		rDTO.setRoleNm("ROLE_USER");
		RoleDTO roleDTO = roleService.getRole(rDTO);
		
		UserRoleRlDTO urDTO = new UserRoleRlDTO();
		urDTO.setUsrRoleRlIdx(UUID.randomUUID().toString().replace("-",""));
		urDTO.setRoleIdx(roleDTO.getRoleIdx());
		urDTO.setUsrIdx(uDTO.getUsrIdx());
		int result2 = userRoleRlService.addUserRole(urDTO);
		
		if(result2 > 0) {
			returnMap.put("result2", "success");
			returnMap.put("msg2", "USER ROLE 저장 성공");
		}else {
			returnMap.put("result2", "fail");
			returnMap.put("msg2", "USER ROLE 저장 실패");
			return returnMap;
		}
		
		return returnMap;
	}
}
