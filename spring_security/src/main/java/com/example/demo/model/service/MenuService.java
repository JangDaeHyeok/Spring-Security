package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.MenuMapper;
import com.example.demo.model.dto.MenuDTO;

@Service
public class MenuService {
	@Autowired MenuMapper menuMapper;
	
	public List<MenuDTO> getMenuList(MenuDTO menuDTO) throws Exception {
		List<MenuDTO> list = menuMapper.selectMenu(menuDTO);
		
		return list;
	}
	
	public MenuDTO getMenu(MenuDTO menuDTO) throws Exception {
		List<MenuDTO> list = menuMapper.selectMenu(menuDTO);
		
		return list.get(0);
	}
}
