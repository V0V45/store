package com.electric.store.model;

import java.util.ArrayList;
import java.util.UUID;

public class ProductStorage {
    private static ArrayList<Product> products = new ArrayList<>();

    static {
        ProductStorage.getProducts().add(new Product("Wire Crimper Tool", 200, UUID.randomUUID().toString()));
		ProductStorage.getProducts().add(new Product("Circuit Breaker", 50, UUID.randomUUID().toString()));
		ProductStorage.getProducts().add(new Product("Terminal Block", 25, UUID.randomUUID().toString()));
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }
    
}
