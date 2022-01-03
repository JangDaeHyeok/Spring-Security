package com.example.demo;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.example.demo.model.service.SecurityUrlMatcherService;
import com.example.demo.model.staticval.AdmMenuStaticValue;
import com.example.demo.model.vo.SecurityUrlMatcher;

@Component
@EnableScheduling
public class DemoInitializer implements ApplicationRunner{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired SqlSessionFactory sf;
	@Autowired private SecurityUrlMatcherService securityUrlMatcherService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.error("*********************************************************************");
		log.error("[나모MS서버] 서버 기동 시작 및 기본 세팅 시작...");
		
		// 연결 DB 정보 확인
		String ds = sf.getConfiguration().getEnvironment().getDataSource().getConnection().getMetaData().getURL();
		String dbmsUrl =  ds.replaceAll("jdbc:mysql://", "");
		dbmsUrl = dbmsUrl.substring(0, dbmsUrl.indexOf("?") > -1 ? dbmsUrl.indexOf("?") : dbmsUrl.length());
		log.error("[나모MS서버] 사용 DB : " + dbmsUrl); 
		
		// 관리자 메뉴-권한 정보 static 할당
		AdmMenuStaticValue.admMenuList = securityUrlMatcherService.getSecurityUrlMatcherList(new SecurityUrlMatcher());
		log.error("[나모MS서버] 관리자 메뉴-권한 정보 저장 완료 : 메뉴-권한 개수 " + AdmMenuStaticValue.admMenuList.size() + "개");
		
		log.error("[나모MS서버] 서버 기동 초기화 완료...");
		log.error("*********************************************************************");
	}
}
