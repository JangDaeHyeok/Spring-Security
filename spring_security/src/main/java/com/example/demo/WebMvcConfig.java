package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title       Spring Security 정적 자원 설정
 * @Author      장대혁
 * @Developer   장대혁
 * @Date        2021-11-11
 * @Description 스프링 시큐리티에 적용하지 않을 정적 자원 설정
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/static/", "classpath:/public/", "classpath:/",
			"classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/" , "classpath:/favicon.ico" };
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// /에 해당하는 url mapping을 /index로 forward
		registry.addViewController("/").setViewName("forward:/index");
		// 우선순위를 가장 높게 설정
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
