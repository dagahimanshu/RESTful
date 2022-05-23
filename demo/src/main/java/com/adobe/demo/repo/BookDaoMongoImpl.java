package com.adobe.demo.repo;

import org.springframework.stereotype.Repository;

@Repository
public class BookDaoMongoImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Stored in MongoDB NoSQL!!!");
	}

}
