package com.example.demo.model.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.UserMapper;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.security.exception.UserNotFoundException;

@Service
public class UserService {
	@Autowired UserMapper userMapper;
	
	// userId로 정보 조회
	public UserDTO loadUserByUsername(String userId) throws Exception {
		UserDTO uDTO = new UserDTO();
		List<UserDTO> list = userMapper.loadUserByUserId(userId);
		if(list == null || list.size() <= 0) {
			new UserNotFoundException(userId);
		}else {
			uDTO = list.get(0);
			
			uDTO.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(uDTO.getRoleNm())));
		}
		
		return uDTO;
	}
	
	// 사용자 정보 저장
	public int addUser(UserDTO userDTO) throws Exception {
		int result = userMapper.insertUser(userDTO);
		
		return result;
	}
}
