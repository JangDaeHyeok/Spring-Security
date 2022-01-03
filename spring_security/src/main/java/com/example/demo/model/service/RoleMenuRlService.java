package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.RoleMenuRlMapper;
import com.example.demo.model.dto.RoleMenuRlDTO;

@Service
public class RoleMenuRlService {
	@Autowired RoleMenuRlMapper roleMenuRlMapper;
	
	public List<RoleMenuRlDTO> getRoleMenuRlList(RoleMenuRlDTO roleMenuRlDTO) throws Exception {
		List<RoleMenuRlDTO> list = roleMenuRlMapper.selectRoleMenuRl(roleMenuRlDTO);
		
		return list;
	}
	
	public int addRoleMenuRl(RoleMenuRlDTO roleMenuRlDTO) throws Exception {
		return roleMenuRlMapper.insertRoleMenuRl(roleMenuRlDTO);
	}
	
	public int chgRoleMenuRlDelete(RoleMenuRlDTO roleMenuRlDTO) throws Exception {
		return roleMenuRlMapper.updateRoleMenuRlDelete(roleMenuRlDTO);
	}
	
	public int delRoleMenuRl(RoleMenuRlDTO roleMenuRlDTO) throws Exception {
		return roleMenuRlMapper.deleteRoleMenuRl(roleMenuRlDTO);
	}
}
