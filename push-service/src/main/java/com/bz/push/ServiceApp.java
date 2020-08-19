package com.bz.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource(locations = { "dubbo-provider.xml"})
@EnableScheduling
public class ServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApp.class, args);
	}
}
