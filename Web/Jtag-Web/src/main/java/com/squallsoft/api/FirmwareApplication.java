package com.squallsoft.api;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.Ordered;

import com.squallsoft.api.config.DBContextHolder;
import com.squallsoft.api.config.DBTypeEnum;


@SpringBootApplication
public class FirmwareApplication  extends SpringBootServletInitializer{
	
	@PostConstruct
    void init() {
		DBContextHolder.setCurrentDb(DBTypeEnum.LOCAL);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FirmwareApplication.class, args);	
	}
}
