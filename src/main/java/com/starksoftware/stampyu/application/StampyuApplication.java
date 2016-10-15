package com.starksoftware.stampyu.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/rest/")
public class StampyuApplication extends Application {

	public StampyuApplication() {
		super();

		BeanConfig beanConfig = new BeanConfig();

		beanConfig.setVersion("1.0");
		beanConfig.setContact("geraldoneto.ads@gmail.com");
		beanConfig.setLicense("Stark Software");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setBasePath("/stampyu-web/rest");
		beanConfig.setHost("localhost:8081");
		beanConfig.setFilterClass("com.starksoftware.library.security.filter.StkApiDocFilter");
		beanConfig.setResourcePackage("com.starksoftware.stampyu");
		beanConfig.setScan(true);
		beanConfig.setPrettyPrint(true);
	}
}
