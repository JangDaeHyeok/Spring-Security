package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
@Data
public class UserDTO implements Serializable{
	private static final long serialVersionUID = -6184044926029805156L;
	
	private String usrIdx                  = null;
	private String usrId                   = null;
	private String usrPw                   = null;
	private String role                    = null;
	
	private String usrRoleRlIdx            = null;
	private String roleIdx                 = null;
	private String parentRoleIdx           = null;
	private String roleNm                  = null;
	
	Collection<? extends GrantedAuthority> authorities;
}
