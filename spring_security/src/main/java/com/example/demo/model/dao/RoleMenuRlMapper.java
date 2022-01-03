package com.example.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.RoleMenuRlDTO;

@Mapper
public interface RoleMenuRlMapper {

	List<RoleMenuRlDTO> selectRoleMenuRl(RoleMenuRlDTO roleMenuRlDTO) throws Exception;
	
	int insertRoleMenuRl(RoleMenuRlDTO roleMenuRlDTO) throws Exception;
	
	int updateRoleMenuRlDelete(RoleMenuRlDTO roleMenuRlDTO) throws Exception;
	
	int deleteRoleMenuRl(RoleMenuRlDTO roleMenuRlDTO) throws Exception;
	
}
