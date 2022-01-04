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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.filter.AdminCustomAuthenticationFilter;
import com.example.demo.security.handler.AdminCustomLoginFailHandler;
import com.example.demo.security.handler.AdminCustomLoginSuccessHandler;
import com.example.demo.security.handler.JwtAuthenticationEntryPoint;
import com.example.demo.security.jwt.JwtRequestFilter;
import com.example.demo.security.provider.AdminCustomAuthenticationProvider;

/**
 * @Title       Spring Security 관리자 설정
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description Spring Security 관리자 세부 설정(session, jwt)
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	// @Autowired private WebAccessDeniedHandler webAccessDeniedHandler;
	// @Autowired private WebAuthenticationEntryPoint webAuthenticationEntryPoint;
	
	@Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired private JwtRequestFilter jwtRequestFilter;
	
	// 정적 자원에 대해서는 Security 설정을 적용하지 않음
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.error("*********************************************************************");
		log.error("[WebSecurityConfig] Spring Security 설정");
		
		/*
		 * session 사용 시
		http.csrf().disable().antMatcher("/admin/**").authorizeRequests()
			// 리로스 항목 제외
			.antMatchers("/static/**").permitAll()
			.antMatchers("/favicon.ico").permitAll()
			
			// 관리자 권한 항목
			.antMatchers("/admin").permitAll()
			.antMatchers("/admin/join").permitAll()
			.antMatchers("/admin/join/**").permitAll()
			.antMatchers("/admin/loginView").permitAll()
			.antMatchers("/admin/login").permitAll()
			.anyRequest().access("@authorizationChecker.check(request, authentication)")
			.and()
			
			// exception 처리
			.exceptionHandling()
			// 권한이 없는 url 접근 시
			.accessDeniedHandler(webAccessDeniedHandler)
			// 인증되지 않은 사용자 접근 시
			// .authenticationEntryPoint(webAuthenticationEntryPoint)
			.and()
			
		// 로그인하는 경우에 대해 설정
		.formLogin()
			// 로그인 페이지를 제공하는 URL을 설정
			.loginPage("/admin/loginView")
			// 로그인 성공 URL을 설정
			.successForwardUrl("/admin")
			// 로그인 실패 URL을 설정
			.failureForwardUrl("/admin/loginView")
			.permitAll()
			.and()
		// 로그아웃 관련 처리
		.logout()
			.logoutUrl("/admin/logout")
			.logoutSuccessUrl("/admin")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and()
		.addFilterBefore(adminCustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		*/
		
		// jwt 사용을 위한 세션 비활성화
		http.csrf().disable().authorizeRequests()
		// 리로스 항목 제외
		.antMatchers("/static/**").permitAll()
		.antMatchers("/favicon.ico").permitAll()
		
		// 관리자 권한 항목
		.antMatchers("/admin").permitAll()
		.antMatchers("/admin/authentication").permitAll()
		.antMatchers("/admin/join").permitAll()
		.antMatchers("/admin/join/**").permitAll()
		.antMatchers("/admin/loginView").permitAll()
		.antMatchers("/admin/login").permitAll()
		.anyRequest().authenticated()
		// jwt가 없는 경우 exception handler 설정
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		// Spring Security에서 session을 생성하거나 사용하지 않도록 설정
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// jwt filter 적용
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		log.error("[WebSecurityConfig] Spring Security 설정 완료");
		log.error("*********************************************************************");
	}
	
	/*
	 * customLoginSuccessHandler를 CustomAuthenticationFilter의 인증 성공 핸들러로 추가
	 * 로그인 성공 시 /admin/login 로그인 url을 체크하고 인증 토큰 발급 
	 */
	@Bean
	public AdminCustomAuthenticationFilter adminCustomAuthenticationFilter() throws Exception {
		AdminCustomAuthenticationFilter customAuthenticationFilter = new AdminCustomAuthenticationFilter(authenticationManager());
		customAuthenticationFilter.setFilterProcessesUrl("/admin/login");
		customAuthenticationFilter.setAuthenticationSuccessHandler(adminCustomLoginSuccessHandler());
		customAuthenticationFilter.setAuthenticationFailureHandler(adminCustomLoginFailHandler());
		customAuthenticationFilter.afterPropertiesSet();
		return customAuthenticationFilter;
	}
	
	// 로그인 성공 시 실행될 handler bean 등록
	@Bean
	public AdminCustomLoginSuccessHandler adminCustomLoginSuccessHandler() {
		return new AdminCustomLoginSuccessHandler();
	}
	
	// 로그인 실패 시 실행될 handler bean 등록
	@Bean
	public AdminCustomLoginFailHandler adminCustomLoginFailHandler() {
		return new AdminCustomLoginFailHandler();
	}
	
	@Bean
	public AdminCustomAuthenticationProvider adminCustomAuthenticationProvider() {
		return new AdminCustomAuthenticationProvider(passwordEncoder);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
		authenticationManagerBuilder.authenticationProvider(adminCustomAuthenticationProvider());
	}
}
