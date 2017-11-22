package com.inrich.VoiceHelper;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.inrich.VoiceHelper.mapper")
public class VoiceHelperApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(VoiceHelperApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(VoiceHelperApplication.class, args);
	}
}
