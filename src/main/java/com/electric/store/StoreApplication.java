package com.electric.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electric.store.model.Product;
import com.electric.store.model.ProductStorage;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ProductStorage.getBooks().add(new Product("Wire Crimper Tool", 200));
		ProductStorage.getBooks().add(new Product("Circuit Breaker", 50));
		ProductStorage.getBooks().add(new Product("Terminal Block", 25));
		SpringApplication.run(StoreApplication.class, args);
	}

}
