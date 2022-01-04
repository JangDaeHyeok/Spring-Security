package com.example.demo.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.AdminMapper;
import com.example.demo.model.dao.RoleMapper;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.dto.RoleDTO;
import com.example.demo.security.exception.AdminNotFoundException;

@Service
public class AdminService {
	@Autowired AdminMapper adminMapper;
	@Autowired RoleMapper roleMapper;
	
	// adminId로 정보 조회
	public List<AdminDTO> getAdmin(AdminDTO adminDTO) throws Exception {
		return adminMapper.selectAdmin(adminDTO);
	}
	
	// adminId로 정보 조회
	public AdminDTO loadAdminByAdminId(String adminId) throws Exception {
		AdminDTO aDTO = new AdminDTO();
		List<AdminDTO> list = adminMapper.loadAdminByAdminId(adminId);
		if(list == null || list.size() == 0) {
			throw new AdminNotFoundException(adminId);
		}else {
			aDTO = list.get(0);
			
			// admin이 가지고있는 모든 권한 등록
			aDTO.setAuthorities(loadAdminAuthArrayByAdminId(aDTO.getAdmId()));
		}
		
		return aDTO;
	}
	
	// adminId로 권한 조회(List<String>)
	public List<AdminDTO> loadAdminAuthByAdminId(String adminId) throws Exception {
		AdminDTO aDTO = new AdminDTO();
		aDTO.setAdmId(adminId);
		return adminMapper.loadAdminAuthByAdminId(aDTO);
	}
	
	// adminId로 권한 조회(List<GrantedAuthority>)
	public List<GrantedAuthority> loadAdminAuthArrayByAdminId(String adminId) throws Exception {
		AdminDTO aDTO = new AdminDTO();
		aDTO.setAdmId(adminId);
		List<AdminDTO> authList = adminMapper.loadAdminAuthByAdminId(aDTO);
		// 임시 권한 저장용(중복 제거X)
		List<String> tmpAuth = new ArrayList<String>();
		// 권한 저장용(중복 제거O)
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		// 권한 리스트 생성(권한은 2뎁스까지 있는 것으로 가정)
		for(AdminDTO auth : authList) {
			// 권한 임시 리스트에 저장
			tmpAuth.add(auth.getRoleNm());
			
			// 해당 권한에 대한 하위권한 조회
			RoleDTO rDTO = new RoleDTO();
			rDTO.setParentRoleIdx(auth.getRoleIdx());
			List<RoleDTO> childRoleList = roleMapper.selectRole(rDTO);
			// 하위권한 임시 리스트에 저장
			for(RoleDTO childDTO : childRoleList) {
				tmpAuth.add(childDTO.getRoleNm());
			}
		}
		
		// 권한 임시 리스트 중복 제거
		Set<String> authSet = new HashSet<String>(tmpAuth);
		tmpAuth = new ArrayList<String>(authSet);
		
		// 중복 제거된 권한 리스트 적용
		for(String auth : tmpAuth) {
			grantedAuthorities.add(new SimpleGrantedAuthority(auth));
		}
		
		return grantedAuthorities;
	}
	
	// 관리자 정보 저장
	public int addAdmin(AdminDTO adminDTO) throws Exception {
		int result = adminMapper.insertAdmin(adminDTO);
		
		return result;
	}
}
