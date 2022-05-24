package com.adobe.prj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

@Component
public class ProductCRUDClient implements CommandLineRunner {
	
	@Autowired
	private OrderService service;
	
	public void printProducts() {
		
	}
	
	private void addProducts() {
		Product[] products = new Product[5]; // Array of 5 Pointers
		products[0] = new Product(133, "Sony Bravia", 135000.00, "LED", 100); // upcasting
		products[1] = new Product(453, "MotoG", 12999.00, "4G", 100);
		products[2] = new Product(634, "Onida Thunder", 3500.00, "CRT", 100);
		products[3] = new Product(621, "iPhone XR", 99999.00, "4G", 100);
		products[4] = new Product(844, "Oppo", 9999.00, "4G", 100);
		
		for(Product p : products) {
			service.insertProduct(p);
		}
	}
	@Override
	public void run(String... args) throws Exception {
//		addProducts();
	}

}
