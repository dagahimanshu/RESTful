package com.adobe.demo.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class BookDaoMySQLImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Stored in MySQL!!!");
	}

}
