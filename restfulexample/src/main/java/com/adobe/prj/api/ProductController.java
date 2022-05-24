package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

@RestController
@RequestMapping("api/products")
public class ProductController {
	@Autowired
	private OrderService service;
	
	// GET
	// http://localhost:8080/api/products
	// http://localhost:8080/api/products?page=2&size=10
	// http://localhost:8080/api/products?low=100&high=5000
	// Accept: application/json ==> jackson
	@GetMapping()
	public @ResponseBody List<Product> 
		getProducts(@RequestParam(name = "low", defaultValue = "0.0") double low, 
				@RequestParam(name = "high", defaultValue = "0.0") double high) {
		if(low == 0.0 && high == 0.0) {
			return service.getProducts();
		} else {
			return service.getProductsByRange(low, high);
		}
	}
	
	// GET
	// http://localhost:8080/api/products/3
	@GetMapping("/{id}")
	public @ResponseBody Product getProductById(@PathVariable("id") int id) {
		return service.getById(id);
	}
	
	// POST
	// http://localhost:8080/api/products
	// content-type:application/json
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody Product p) {
		p = service.insertProduct(p);
		return new ResponseEntity<Product>(p, HttpStatus.CREATED);
	}
	
	// PUT
	// http://localhost:8080/api/products/3
	@PutMapping("/{id}")
	public @ResponseBody Product updateProduct(@PathVariable("id") int id, @RequestBody Product p) {
		service.updateProduct(p.getQuantity(), id);
		return service.getById(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") int id) {
		
	}
}
