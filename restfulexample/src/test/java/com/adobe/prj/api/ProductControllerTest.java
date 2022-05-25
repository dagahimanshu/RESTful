package com.adobe.prj.api;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@MockBean
	private OrderService service;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getProductsTest() throws Exception {
		List<Product> products = Arrays.asList(new Product(1,"a",500.00, "c1", 100), 
				new Product(2,"b",500.00, "c2", 100));
		
		when(service.getProducts()).thenReturn(products); // mocking
		
		mockMvc.perform(get("/api/products"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].name", is("a")));
		
		verify(service, times(1)).getProducts();
	}
	
	@Test
	public void addProductTest() throws Exception {
		Product p = new Product(0, "test", 1500.00, "c1", 100);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		
		when(service.insertProduct(Mockito.any(Product.class)))
			.thenReturn(Mockito.any(Product.class));
	
		mockMvc.perform(post("/api/products")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		verify(service, times(1)).insertProduct(Mockito.any(Product.class));
	}
	
	@Test
	public void addProductExceptionTest() throws Exception {
		Product p = new Product(0, "", -1500.00, "c1", -1);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p);
		
//		when(service.insertProduct(Mockito.any(Product.class)))
//			.thenReturn(Mockito.any(Product.class));
	
		mockMvc.perform(post("/api/products")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.errors", hasSize(3)))
			.andExpect(jsonPath("$.errors", hasItem("Name is required")));
		
		verifyNoInteractions(service);
	}
}
