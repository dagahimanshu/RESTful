package com.adobe.prj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adobe.prj.entity.Product;

@Configuration
public class RestClient  {
 
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public void getProducts(RestTemplate template) {
		String result = template.getForObject("http://localhost:8080/api/products", String.class);
		System.out.println(result);
	}
	
	public void getProduct(RestTemplate template) {
		 ResponseEntity<Product> response = 
				 	template.getForEntity("http://localhost:8080/api/products/2", Product.class);
		System.out.println(response.getBody().getName());
		
	}
	
	public void addProduct(RestTemplate template) {
			Product p = new Product(0, "Samsung Washing Machine", 34000.00, "electrical", 100);
			ResponseEntity<Product> response =  
					template.postForEntity("http://localhost:8080/api/products", p, Product.class);
			
			System.out.println(response.getStatusCodeValue()); // 201
			
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			addProduct(restTemplate);
		};
	}
	
	
	
}
