package com.adobe.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.adobe.demo.service.SampleService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	@Autowired
	SampleService service;
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		
//		String[] names = ctx.getBeanDefinitionNames();
//		for(String name: names) {
//			System.out.println(name);
//		}
		// pull the bean from container
//		SampleService service = ctx.getBean("sampleService", SampleService.class);
//		service.createBook();
	}

	@Override
	public void run(String... args) throws Exception {
		// code here executes only on contanier creation completed
		service.createBook();
	}

}
