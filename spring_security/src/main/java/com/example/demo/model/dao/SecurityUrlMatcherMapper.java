package com.example.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.vo.SecurityUrlMatcher;

@Mapper
public interface SecurityUrlMatcherMapper {

	List<SecurityUrlMatcher> selectSecurityUrlMatcherMapper(SecurityUrlMatcher securityUrlMatcher) throws Exception;
	
}
