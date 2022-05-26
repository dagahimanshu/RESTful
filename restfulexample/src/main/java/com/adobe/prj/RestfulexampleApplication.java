package com.adobe.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableHypermediaSupport(type = HypermediaType.HAL_FORMS)
public class RestfulexampleApplication {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Scheduled(cron  = "0 0/30 * * * *")
	public void clearCache() {
		System.out.println("Cache Clear!!!");
		cacheManager.getCacheNames().forEach(name -> {
			cacheManager.getCache(name).clear();
		});
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(RestfulexampleApplication.class, args);
	}

}
