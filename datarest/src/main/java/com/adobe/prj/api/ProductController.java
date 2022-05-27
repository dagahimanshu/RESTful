package com.adobe.prj.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Product;

@BasePathAwareController
public class ProductController {
	@Autowired
	ProductDao productDao;
	 
	@RequestMapping(path="products", method = RequestMethod.GET)
	public @ResponseBody List<Product> get() {
		return Arrays.asList(new Product(44,"a", 4545.22, "c1", 100));
	}
}
