package com.example.demo.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.UserRoleRlDTO;

@Mapper
public interface UserRoleRlMapper {
	
	int insertUserRoleRl(UserRoleRlDTO userRoleDTO) throws Exception;
	
}
