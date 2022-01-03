package com.example.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.MenuDTO;

@Mapper
public interface MenuMapper {

	List<MenuDTO> selectMenu(MenuDTO menuDTO) throws Exception;
	
}
