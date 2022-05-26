package com.adobe.prj.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.adobe.prj.service.NotFoundException;
import com.adobe.prj.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/products")
@Validated
@Tag(name="products", description = "the product API")
public class ProductController {
	@Autowired
	private OrderService service;
	
	@Cacheable(value="productCache", key="#id")
	@GetMapping("/cache/{id}")
	public @ResponseBody Product getProductCache(@PathVariable("id") int id) throws NotFoundException {
		System.out.println("Cache miss!!!!");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return service.getById(id);
	}
	
	
	@GetMapping("/etag/{id}")
	public ResponseEntity<Product> getProductCacheById(@PathVariable("id") int id) throws NotFoundException {
		Product p = service.getById(id);
		return ResponseEntity.ok().eTag(Long.toString(p.getVersion())).body(p);
	}
	
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
	@Operation(summary = "Get a product by its ID")
	@ApiResponses(value =  {
			@ApiResponse(responseCode = "200" , description = "Found product for given id"),
			@ApiResponse(responseCode = "404" , description = "product for given id Not Found")
	})
	@GetMapping("/{id}")
	public @ResponseBody Product getProductById(@PathVariable("id") int id) throws NotFoundException {
		return service.getById(id);
	}
	
	// POST
	// http://localhost:8080/api/products
	// content-type:application/json
	@Cacheable(value="productCache", key="#p.id", condition = "#p.price > 20000")
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
		p = service.insertProduct(p);
		return new ResponseEntity<Product>(p, HttpStatus.CREATED);
	}
	
	// PUT
	// http://localhost:8080/api/products/3
	@CachePut(value="productCache", key="#id")
	@PutMapping("/{id}")
	public @ResponseBody Product updateProduct(@PathVariable("id") int id, @RequestBody Product p) throws NotFoundException {
		service.updateProduct(p.getQuantity(), id);
		return service.getById(id);
	}
	
	@CacheEvict(value="productCache", key="#id")
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") int id) {
		
	}
	
	@GetMapping("/clear")
	@CacheEvict(value="productCache", allEntries = true)
	public @ResponseBody String clear() {
		return "cacche cleared!!!";
	}
}
