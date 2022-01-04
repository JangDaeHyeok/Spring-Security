package com.example.demo.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.filter.UsrCustomAuthenticationFilter;
import com.example.demo.security.handler.UserCustomLoginFailHandler;
import com.example.demo.security.handler.UsrCustomLoginSuccessHandler;
import com.example.demo.security.provider.UsrCustomAuthenticationProvider;

/**
 * @Title       Spring Security 사용자 설정
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description Spring Security 사용자 세부 설정(session, jwt)
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class UsrSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	// 정적 자원에 대해서는 Security 설정을 적용하지 않음
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.error("*********************************************************************");
		log.error("[WebSecurityConfig] Spring Security 설정");
		
		http.csrf().disable().antMatcher("/**").authorizeRequests()
			/*
			.antMatchers("/**").authenticated()
			.antMatchers("/**").annonymous()
			 */
			
			// 리로스 항목 제외
			.antMatchers("/static/**").permitAll()
			.antMatchers("/favicon.ico").permitAll()
			
			// 사용자 로그인 spring security 적용 제외 항목
			.antMatchers("/").permitAll()
			.antMatchers("/index").permitAll()
			.antMatchers("/user/**").permitAll()
			
			// user login 권한 체크용 url
			.antMatchers("/loginCheck").hasAnyRole("ADMIN", "USER")
			
			// 그 외 항목 전부 인증 적용
			.anyRequest()
			.authenticated()
			.and()
		// 로그인하는 경우에 대해 설정
		.formLogin()
			// 로그인 페이지를 제공하는 URL을 설정
			.loginPage("/user/loginView")
			// 로그인 성공 URL을 설정
			.successForwardUrl("/hello")
			// 로그인 실패 URL을 설정
			.failureForwardUrl("/user/loginView")
			.permitAll()
			.and()
		// 로그아웃 관련 처리
		.logout()
			.logoutUrl("/user/logout")
			.logoutSuccessUrl("/user/loginView")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and()
		.addFilterBefore(usrCustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		log.error("[WebSecurityConfig] Spring Security 설정 완료");
		log.error("*********************************************************************");
	}
	
	/*
	 * customLoginSuccessHandler를 CustomAuthenticationFilter의 인증 성공 핸들러로 추가
	 * 로그인 성공 시 /user/login 로그인 url을 체크하고 인증 토큰 발급 
	 */
	@Bean
	public UsrCustomAuthenticationFilter usrCustomAuthenticationFilter() throws Exception {
		UsrCustomAuthenticationFilter customAuthenticationFilter = new UsrCustomAuthenticationFilter(authenticationManager());
		customAuthenticationFilter.setFilterProcessesUrl("/user/login");
		customAuthenticationFilter.setAuthenticationSuccessHandler(usrCustomLoginSuccessHandler());
		customAuthenticationFilter.setAuthenticationFailureHandler(usrCustomLoginFailHandler());
		customAuthenticationFilter.afterPropertiesSet();
		return customAuthenticationFilter;
	}
	
	// 로그인 성공 시 실행될 handler bean 등록
	@Bean
	public UsrCustomLoginSuccessHandler usrCustomLoginSuccessHandler() {
		return new UsrCustomLoginSuccessHandler();
	}
	
	// 로그인 성공 시 실행될 handler bean 등록
	@Bean
	public UserCustomLoginFailHandler usrCustomLoginFailHandler() {
		return new UserCustomLoginFailHandler();
	}
	
	@Bean
	public UsrCustomAuthenticationProvider usrCustomAuthenticationProvider() {
		return new UsrCustomAuthenticationProvider(passwordEncoder);
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
		authenticationManagerBuilder.authenticationProvider(usrCustomAuthenticationProvider());
	}
}
