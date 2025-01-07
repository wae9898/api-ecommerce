package com.apex.apiecommerce.service.impl;

import com.apex.apiecommerce.exception.DatabaseOperationException;
import com.apex.apiecommerce.model.Product;
import com.apex.apiecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_Success() {
        Product product = new Product();
        product.setName("Auriculares Bluetooth");
        product.setDescription("Auriculares inalámbricos con cancelación de ruido.");
        product.setPrice(10.0);
        product.setStock(5);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("Auriculares Bluetooth", createdProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProduct_DatabaseException() {
        Product product = new Product();
        product.setName("Auriculares Bluetooth");

        when(productRepository.save(any(Product.class))).thenThrow(new DataAccessException("Error") {});

        assertThrows(DatabaseOperationException.class, () -> productService.createProduct(product));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetAllProducts_Success() {
        Product product1 = new Product();
        product1.setName("Auriculares Bluetooth");

        Product product2 = new Product();
        product2.setName("Auriculares Bluetooth 2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_DatabaseException() {
        when(productRepository.findAll()).thenThrow(new DataAccessException("Error") {});

        assertThrows(DatabaseOperationException.class, () -> productService.getAllProducts());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        Product product = new Product();
        product.setName("Auriculares Bluetooth");

        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById("1");

        assertNotNull(foundProduct);
        assertEquals("Auriculares Bluetooth", foundProduct.getName());
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(DatabaseOperationException.class, () -> productService.getProductById("1"));
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testUpdateProduct_Success() {
        Product existingProduct = new Product();
        existingProduct.setName("Auriculares Bluetooth");

        Product updatedProduct = new Product();
        updatedProduct.setName("Auriculares Bluetooth Sony");

        when(productRepository.findById("1")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct("1", updatedProduct);

        assertNotNull(result);
        assertEquals("Auriculares Bluetooth Sony", result.getName());
        verify(productRepository, times(1)).findById("1");
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setName("Auriculares Bluetooth");

        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(DatabaseOperationException.class, () -> productService.updateProduct("1", updatedProduct));
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteProduct_Success() {
        when(productRepository.existsById("1")).thenReturn(true);
        doNothing().when(productRepository).deleteById("1");

        assertDoesNotThrow(() -> productService.deleteProduct("1"));
        verify(productRepository, times(1)).existsById("1");
        verify(productRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.existsById("1")).thenReturn(false);

        assertThrows(DatabaseOperationException.class, () -> productService.deleteProduct("1"));
        verify(productRepository, times(1)).existsById("1");
    }
}