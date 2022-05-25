package com.adobe.prj.api;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

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
}
