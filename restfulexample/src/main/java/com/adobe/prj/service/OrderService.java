package com.adobe.prj.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.OrderDao;
import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Item;
import com.adobe.prj.entity.Order;
import com.adobe.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao; // interface
	
	@Autowired
	private OrderDao orderDao;
	
	public List<Order> getOrders() {
		return orderDao.findAll();
	}
	
	/* 
	  	{
			"customer": {
				"email": "sam@adobe.com"
			},
		"items": [
				{"product": {"id": 2}, "qty": 2},
		 		{"product": {"id": 6}, "qty": 4}
		]
		}
	 */
	@Transactional
	public Order placeOrder(Order o) {
		List<Item> items = o.getItems();
		double total = 0.0;
		for(Item item : items) {
			Product p = productDao.findById(item.getProduct().getId()).get();
			if(p.getQuantity() <= 0) {
				throw new IllegalArgumentException("Product " + p.getName() + " not in stock!!!");
			}
			p.setQuantity(p.getQuantity() - item.getQty()); // dirty checking
			item.setAmount(p.getPrice() * item.getQty());
			total += item.getAmount();
		}
		o.setTotal(total);
		return orderDao.save(o); // cascade takes care of persiting items
	}
	
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
