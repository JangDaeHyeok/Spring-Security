package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.AdminRoleRlMapper;
import com.example.demo.model.dto.AdminRoleRlDTO;

@Service
public class AdminRoleRlService {
	@Autowired AdminRoleRlMapper adminRoleMapper;

	// 관리자 ROLE 정보 저장
	public int addAdminRole(AdminRoleRlDTO adminRoleDTO) throws Exception {
		int result = adminRoleMapper.insertAdminRoleRl(adminRoleDTO);
		
		return result;
	}
	
	// 관리자 ROLE 정보 수정
	public int chgAdminRoleByAdmIdx(AdminRoleRlDTO adminRoleDTO) throws Exception {
		int result = adminRoleMapper.updateAdminRoleRlByAdmIdx(adminRoleDTO);
		
		return result;
	}
}
