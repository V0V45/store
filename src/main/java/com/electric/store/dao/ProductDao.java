package com.electric.store.dao;

import java.util.List;
import com.electric.store.model.Product;

public interface ProductDao {
    List<Product> findAll();
    Product save(Product product);
    Product getById(String productId);
    Product update(Product product);
    void delete(Product product);
    void deleteAll();
}
