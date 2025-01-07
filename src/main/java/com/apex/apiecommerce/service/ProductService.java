package com.apex.apiecommerce.service;

import com.apex.apiecommerce.model.Product;

import java.util.List;


public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(String id);

    Product updateProduct(String id, Product updatedProduct);

    void deleteProduct(String id);
}
