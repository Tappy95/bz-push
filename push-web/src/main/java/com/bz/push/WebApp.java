package com.bz.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ImportResource({ "classpath:dubbo-consumer.xml" })
public class WebApp { 
	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}
}
