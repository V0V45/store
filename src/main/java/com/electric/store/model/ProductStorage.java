package com.electric.store.model;

import java.util.ArrayList;

public class ProductStorage {
    private static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Product> getProducts() {
        return products;
    }
    
}
