package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.RoleMapper;
import com.example.demo.model.dto.RoleDTO;

@Service
public class RoleService {
	@Autowired RoleMapper roleMapper;
	
	public List<RoleDTO> getRoleList(RoleDTO roleDTO) throws Exception {
		List<RoleDTO> list = roleMapper.selectRole(roleDTO);
		
		return list;
	}
	
	public RoleDTO getRole(RoleDTO roleDTO) throws Exception {
		List<RoleDTO> list = roleMapper.selectRole(roleDTO);
		
		return list.get(0);
	}
}
