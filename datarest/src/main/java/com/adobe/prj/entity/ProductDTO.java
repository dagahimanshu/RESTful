package com.adobe.prj.entity;

import org.springframework.data.rest.core.config.Projection;

@Projection(
		name="productdata",
		types = {Product.class})
public interface ProductDTO {
	String getName();
	double getPrice();
}
