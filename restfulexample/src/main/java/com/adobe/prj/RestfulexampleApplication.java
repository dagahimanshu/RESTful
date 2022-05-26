package com.adobe.prj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestfulexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulexampleApplication.class, args);
	}

}
