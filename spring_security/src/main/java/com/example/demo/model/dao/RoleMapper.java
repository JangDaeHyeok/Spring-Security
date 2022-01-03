package com.example.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.RoleDTO;

@Mapper
public interface RoleMapper {

	List<RoleDTO> selectRole(RoleDTO roleDTO) throws Exception;
	
}
