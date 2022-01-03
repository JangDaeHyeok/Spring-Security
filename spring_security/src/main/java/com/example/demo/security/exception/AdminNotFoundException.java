package com.example.demo.security.exception;

/**
 * @Title       Spring Security Admin Not Fount Exception
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description 관리자가 존재하지 않는 경우 Exception
 */
@SuppressWarnings("serial")
public class AdminNotFoundException extends RuntimeException{
	public AdminNotFoundException(String adminId){
		super("adminId ==> '" + adminId + "' NotFoundException");
	}
}
