package com.adobe.prj.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao; // interface
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public List<Product> getProductsByRange(double low, double high) {
		return productDao.queryByRange(low, high);
	}
	
	public Product insertProduct(Product p) {
		return productDao.save(p);
	}
	
	public Product getById(int id) throws NotFoundException {
//		return productDao.getById(id); // lazy fetching
		Optional<Product> opt = productDao.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		} else {
			throw new NotFoundException("Product with id " + id + " doesn't exist!!!");
		}
	}
	
	@Transactional
	public void updateProduct(int qty, int id) {
		productDao.updateProduct(qty, id);
	}
}
