package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.UserRoleRlMapper;
import com.example.demo.model.dto.UserRoleRlDTO;

@Service
public class UserRoleRlService {
	@Autowired UserRoleRlMapper userRoleMapper;

	// 사용자 정보 저장
	public int addUserRole(UserRoleRlDTO userRoleDTO) throws Exception {
		int result = userRoleMapper.insertUserRoleRl(userRoleDTO);
		
		return result;
	}
}
