package com.example.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.UserDTO;

@Mapper
public interface UserMapper {

	List<UserDTO> loadUserByUserId(String userId) throws Exception;
	
	int insertUser(UserDTO userDTO) throws Exception;
	
}
