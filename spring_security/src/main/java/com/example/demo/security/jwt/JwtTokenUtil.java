package com.example.demo.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.demo.model.dto.AdminDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @Title       JWT Token Util
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2022-01-04
 * @Description JWT 생성, 조회, 유효성 체크
 * @Warning     sevret 변수를 이용해 토근을 검증하기 때문에 실제 사용 시 별도 처리 필요
 */
@Component
public class JwtTokenUtil {
	private static final String secret = "jangdaehyeok";
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	// token으로 사용자 id 조회
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getId);
	}
	
	// token으로 사용자 속성정보 조회
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	// 모든 token에 대한 사용자 속성정보 조회
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	// 토근 만료 여부 체크
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	// 토큰 만료일자 조회
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	// id를 입력받아 토근 생성
	public String generateToken(String id) {
		return generateToken(id, new HashMap<>());
	}
	
	// id, 속성정보를 이용해 토근 생성
	public String generateToken(String id, Map<String, Object> claims) {
		return doGenerateToken(id, claims);
	}
	
	// jwt 토큰 생성
	private String doGenerateToken(String id, Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setId(id)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	// 토근 검증
	public Boolean validateToken(String token, AdminDTO adminDTO) {
		final String username = getUsernameFromToken(token);
		return (username.equals(adminDTO.getAdmId())) && !isTokenExpired(token);
	}
}
