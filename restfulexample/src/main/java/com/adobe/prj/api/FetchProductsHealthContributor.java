package com.adobe.prj.api;

import java.util.Iterator;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;

@Component("fetch-products-health")
public class FetchProductsHealthContributor implements CompositeHealthContributor {

	@Override
	public HealthContributor getContributor(String name) {
		return null;
	}

	@Override
	public Iterator<NamedContributor<HealthContributor>> iterator() {
		return null;
	}
 }
