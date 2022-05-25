package com.adobe.prj.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
public class Order {
 	private int oid;
	
 	private Date orderDate = new Date();
	
	private double total;
 	private Customer customer;
	
 	private List<Item> items = new ArrayList<>();

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
