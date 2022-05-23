package com.adobe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.demo.repo.BookDao;

@Service
public class SampleService {
	@Autowired // @Inject
	private BookDao bookDao;
	
	public void createBook() {
		bookDao.addBook();
	}
}
