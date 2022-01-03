package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.MenuDTO;
import com.example.demo.model.service.MenuService;

@RestController
public class TestAdminMenuController {
	@Autowired MenuService menuService;
	
	@RequestMapping(value="admin/menu/mgmt", method=RequestMethod.GET)
	public Map<String, Object> TestAdminMenuGet(@RequestParam Map<String, Object> input, HttpServletRequest req) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<MenuDTO> list = menuService.getMenuList(new MenuDTO());
		
		returnMap.put("list", list);
		returnMap.put("msg", "메뉴 조회");
		return returnMap;
	}
}
