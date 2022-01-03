package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
@Data
public class AdminDTO implements Serializable{
	private static final long serialVersionUID = -489607591524088566L;
	
	private String admIdx                  = null;
	private String admId                   = null;
	private String admPw                   = null;
	private String role                    = null;
	
	private String admRoleRlIdx            = null;
	private String roleIdx                 = null;
	private String parentRoleIdx           = null;
	private String roleNm                  = null;
	
	Collection<? extends GrantedAuthority> authorities;
}
