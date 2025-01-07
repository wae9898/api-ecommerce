package com.apex.apiecommerce.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.afterPropertiesSet();
        validator = factory.getValidator();
    }

    @Test
    void testValidProduct() {
        Product product = new Product();
        product.setProductId("P001");
        product.setDescription("Smartphone con 128GB de almacenamiento y cámara de 64MP");
        product.setName("Smartphone XYZ");
        product.setPrice(10.99);
        product.setStock(100);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testProductWithInvalidPrice() {
        Product product = new Product();
        product.setProductId("P001");
        product.setDescription("Smartphone con 128GB de almacenamiento y cámara de 64MP");
        product.setName("Smartphone XYZ");
        product.setPrice(-5.0);
        product.setStock(100);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Price must be greater than or equal to 0")));
    }

    @Test
    void testProductWithInvalidStock() {
        Product product = new Product();
        product.setProductId("P001");
        product.setDescription("Smartphone con 128GB de almacenamiento y cámara de 64MP");
        product.setName("Smartphone XYZ");
        product.setPrice(10.99);
        product.setStock(-10);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Stock must be greater than or equal to 0")));
    }

    @Test
    void testProductWithMissingFields() {
        Product product = new Product();
        // Solo establece el nombre para simular campos faltantes.
        product.setName("Auriculares Bluetooth");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Product ID is mandatory")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Price is mandatory")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Stock is mandatory")));
    }

    @Test
    void testPrePersist() {
        Product product = new Product();
        product.prePersist();

        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertEquals(product.getCreatedAt(), product.getUpdatedAt());
    }

    @Test
    void testPreUpdate() throws InterruptedException {
        Product product = new Product();
        product.prePersist();

        LocalDateTime createdAt = product.getCreatedAt();

        Thread.sleep(10);

        product.preUpdate();

        assertNotNull(product.getUpdatedAt());
        assertNotEquals(createdAt, product.getUpdatedAt());
    }

    @Test
    void testSetterGetter() {
        Product product = new Product();
        product.setId("123");
        product.setProductId("P001");
        product.setDescription("Auriculares inalámbricos con cancelación de ruido.");
        product.setName("Auriculares Bluetooth");
        product.setPrice(15.50);
        product.setStock(50);

        assertEquals("123", product.getId());
        assertEquals("P001", product.getProductId());
        assertEquals("Auriculares inalámbricos con cancelación de ruido.", product.getDescription());
        assertEquals("Auriculares Bluetooth", product.getName());
        assertEquals(15.50, product.getPrice());
        assertEquals(50, product.getStock());
    }

}