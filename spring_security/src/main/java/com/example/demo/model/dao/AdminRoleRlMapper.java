package com.example.demo.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.AdminRoleRlDTO;

@Mapper
public interface AdminRoleRlMapper {
	
	int insertAdminRoleRl(AdminRoleRlDTO adminRoleDTO) throws Exception;
	
	int updateAdminRoleRlByAdmIdx(AdminRoleRlDTO adminRoleDTO) throws Exception;
	
}
