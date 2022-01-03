package com.example.demo.security.exception;

/**
 * @Title       Spring Security User Not Fount Exception
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description 사용자가 존재하지 않는 경우 Exception
 */
@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(String userId){
		super("userId ==> '" + userId + "' NotFoundException");
	}
}
