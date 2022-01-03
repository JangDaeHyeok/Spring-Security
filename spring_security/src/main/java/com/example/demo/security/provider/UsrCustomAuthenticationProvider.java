package com.example.demo.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @Title       Spring Security 사용자 인증 과정 수행
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description AuthenticationManager는 전달받은 UsernamePasswordToken을 순차적으로 AuthenticaionProvider들에게 전달하여 실제 인증의 과정을 수행
 */
@RequiredArgsConstructor
public class UsrCustomAuthenticationProvider implements AuthenticationProvider {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("*********************************************************************");
		log.info("[UsrCustomAuthenticationProvider] Spring Security 사용자 정보 인증(Authentication) 조회");
		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		// AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
		String userId = token.getName();
		String userPw = (String) token.getCredentials();
		
		log.info("userId : " + userId);
		log.info("userPw : " + userPw);
		
		// UserDetailsService를 통해 DB에서 아이디로 사용자 조회
		UserDTO uDTO = new UserDTO();
		try {
			uDTO = (UserDTO) userService.loadUserByUsername(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		// 비밀번호가 일치하는지 확인
		log.info("[UsrCustomAuthenticationProvider] BCryptPasswordEncoder 비밀번호 일치 여부 체크");
		log.info("입력한 비밀번호 : " + userPw);
		log.info("사용자 비밀번호 : " + uDTO.getUsrPw());
		
		if (!passwordEncoder.matches(userPw, uDTO.getUsrPw())) {
			log.info("[UsrCustomAuthenticationProvider] **비밀번호 불일치 Exception**");
			log.info("*********************************************************************");
			throw new BadCredentialsException(uDTO.getUsrId() + " Invalid password");
		}
		
		log.info("[UsrCustomAuthenticationProvider] 사용자 인증(Authentication) 성공!!");
		log.info("*********************************************************************");
		
		return new UsernamePasswordAuthenticationToken(uDTO, userPw, uDTO.getAuthorities());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
