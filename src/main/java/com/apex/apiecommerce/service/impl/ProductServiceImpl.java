package com.apex.apiecommerce.service.impl;

import com.apex.apiecommerce.exception.DatabaseOperationException;
import com.apex.apiecommerce.exception.ProductNotFoundException;
import com.apex.apiecommerce.model.Product;
import com.apex.apiecommerce.repository.ProductRepository;
import com.apex.apiecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        try {
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(product);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al crear el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al obtener los productos: " + e.getMessage());
        }
    }

    @Override
    public Product getProductById(String id) {
        try {
            return productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
        } catch (DataAccessException | ProductNotFoundException e) {
            throw new DatabaseOperationException("Error al obtener el producto: " + e.getMessage());
        }
    }

    @Override
    public Product updateProduct(String id, Product updatedProduct) {
        try {
            Product existingProduct = getProductById(id);

            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setUpdatedAt(LocalDateTime.now());

            return productRepository.save(existingProduct);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(String id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new ProductNotFoundException("Producto no encontrado con ID: " + id);
            }
            productRepository.deleteById(id);
        } catch (DataAccessException | ProductNotFoundException e) {
            throw new DatabaseOperationException("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
