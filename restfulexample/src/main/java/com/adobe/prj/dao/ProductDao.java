package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{
	// select * from products where category = ?
	List<Product> findByCategory(String category);
	
	// select * from products where category = ? and price = ?
	List<Product> findByCategoryAndPrice(String category, double price);
	
	//@Query(value = "select * from products p where p.price >= :l and p.price <= :h", nativeQuery=true)
	@Query("from Product p where p.price >= :l and p.price <= :h")
	List<Product> queryByRange(@Param("l") double low, @Param("h") double high);
	
	@Modifying
	@Query("update Product set quantity = :qty where id = :id")
	void updateProduct(@Param("qty") int quantity, @Param("id") int id);
}
