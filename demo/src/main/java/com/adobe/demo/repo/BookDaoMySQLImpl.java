package com.adobe.demo.repo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnMissingBean(type = "BookDao.class")
public class BookDaoMySQLImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Stored in MySQL!!!");
	}

}
