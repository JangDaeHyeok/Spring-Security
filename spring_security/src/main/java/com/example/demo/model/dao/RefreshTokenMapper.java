package com.example.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.RefreshTokenDTO;
import com.example.demo.model.dto.RoleDTO;

@Mapper
public interface RefreshTokenMapper {

	List<RefreshTokenDTO> selectRefreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception;
	
	int selectRefreshTokenCount(RefreshTokenDTO refreshTokenDTO) throws Exception;
	
	int insertRefreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception;
	
	int updateRefreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception;
	
	int deleteRefreshToken(String adminRefreshTokenIdx) throws Exception;
}
