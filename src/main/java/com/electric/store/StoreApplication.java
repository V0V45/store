package com.electric.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electric.store.model.Product;
import com.electric.store.model.ProductStorage;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ProductStorage.getProducts().add(new Product("Wire Crimper Tool", 200));
		ProductStorage.getProducts().add(new Product("Circuit Breaker", 50));
		ProductStorage.getProducts().add(new Product("Terminal Block", 25));
		SpringApplication.run(StoreApplication.class, args);
	}

}
