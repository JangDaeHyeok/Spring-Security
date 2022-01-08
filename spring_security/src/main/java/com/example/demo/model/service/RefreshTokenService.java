package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.RefreshTokenMapper;
import com.example.demo.model.dto.RefreshTokenDTO;
import com.example.demo.model.dto.RoleDTO;

@Service
public class RefreshTokenService {
	@Autowired RefreshTokenMapper refreshTokenMapper;
	
	public RefreshTokenDTO getRefreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
		List<RefreshTokenDTO> list = refreshTokenMapper.selectRefreshToken(refreshTokenDTO);
		
		return list.get(0);
	}
	
	public void addRefreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception {
		int count = refreshTokenMapper.selectRefreshTokenCount(refreshTokenDTO);
		
		// 이미 토큰이 존재하는 경우 update
		if(count > 0) {
			refreshTokenMapper.updateRefreshToken(refreshTokenDTO);
		}
		// 토큰이 존재하지 않는 경우 insert
		else {
			refreshTokenMapper.insertRefreshToken(refreshTokenDTO);
		}
	}
	
	public int delRefreshToken(String adminRefreshTokenIdx) throws Exception {
		return refreshTokenMapper.deleteRefreshToken(adminRefreshTokenIdx);
	}
}
