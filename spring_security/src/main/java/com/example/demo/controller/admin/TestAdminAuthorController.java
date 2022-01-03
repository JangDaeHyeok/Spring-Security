package com.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.RoleMenuRlDTO;
import com.example.demo.model.service.RoleMenuRlService;

@RestController
public class TestAdminAuthorController {
	@Autowired RoleMenuRlService roleMenuRlService;
	
	@RequestMapping(value="admin/author/mgmt", method=RequestMethod.GET)
	public Map<String, Object> TestAdminAuthorGet(@RequestParam Map<String, Object> input, HttpServletRequest req) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		RoleMenuRlDTO rmDTO = new RoleMenuRlDTO();
		if(input.get("roleMenuRlIdx") != null) {
			rmDTO.setRoleMenuRlIdx(input.get("roleMenuRlIdx").toString());
		}
		if(input.get("roleIdx") != null) {
			rmDTO.setRoleIdx(input.get("roleIdx").toString());
		}
		if(input.get("menuIdx") != null) {
			rmDTO.setMenuIdx(input.get("menuIdx").toString());
		}
		List<RoleMenuRlDTO> list = roleMenuRlService.getRoleMenuRlList(rmDTO);
		returnMap.put("list", list);
		
		returnMap.put("msg", "권한목록 조회");
		return returnMap;
	}
	
	@RequestMapping(value="admin/author/mgmt", method=RequestMethod.POST)
	public Map<String, Object> TestAdminAuthorAdd(@RequestBody Map<String, Object> input, HttpServletRequest req) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int result = 1;
		
		RoleMenuRlDTO rmDTO = new RoleMenuRlDTO();
		
		// 기존에 존재하는 권한 메뉴 정보 삭제
		rmDTO.setRoleIdx(input.get("roleIdx").toString());
		roleMenuRlService.delRoleMenuRl(rmDTO);
		
		@SuppressWarnings("unchecked")
		List<String> menuIdxList = (List<String>) input.get("menuIdxList");
		// menu idx 중복 제거
		Set<String> menuIdxSet = new HashSet<>(menuIdxList);
		menuIdxList = new ArrayList<>(menuIdxSet);
		
		for(String menuIdx : menuIdxList) {
			rmDTO.setRoleMenuRlIdx(UUID.randomUUID().toString().replace("-",""));
			rmDTO.setMenuIdx(menuIdx);
			
			result = roleMenuRlService.addRoleMenuRl(rmDTO);
		}
		
		if(result > 0) {
			returnMap.put("result", "success");
			returnMap.put("msg", "권한목록 등록 성공");
		}else {
			returnMap.put("result", "fail");
			returnMap.put("msg", "권한목록 등록 실패");
			return returnMap;
		}
		return returnMap;
	}
}
