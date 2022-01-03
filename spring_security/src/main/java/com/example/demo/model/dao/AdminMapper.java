package com.example.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.AdminDTO;

@Mapper
public interface AdminMapper {

	List<AdminDTO> selectAdmin(AdminDTO adminDTO) throws Exception;
	
	List<AdminDTO> loadAdminByAdminId(String adminId) throws Exception;
	
	List<AdminDTO> loadAdminAuthByAdminId(AdminDTO adminDTO) throws Exception;
	
	int insertAdmin(AdminDTO adminDTO) throws Exception;
	
}
