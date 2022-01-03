package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.SecurityUrlMatcherMapper;
import com.example.demo.model.vo.SecurityUrlMatcher;

@Service
public class SecurityUrlMatcherService {
	@Autowired SecurityUrlMatcherMapper securityUrlMatcherMapper;
	
	public List<SecurityUrlMatcher> getSecurityUrlMatcherList(SecurityUrlMatcher securityUrlMatcher) throws Exception {
		List<SecurityUrlMatcher> list = securityUrlMatcherMapper.selectSecurityUrlMatcherMapper(securityUrlMatcher);
		
		return list;
	}
}
