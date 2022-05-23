package com.adobe.demo.repo;

import org.springframework.stereotype.Repository;

@Repository
public class BookDaoMySQLImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Stored in MySQL!!!");
	}

}