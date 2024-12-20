package com.ecommerce.whishlist.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductTest {

    @Test
    void testProductConstructor_ShouldCreateProductWithGivenIdAndName() {
        Product product = new Product("1", "Product 1");

        assertEquals("1", product.getId());
        assertEquals("Product 1", product.getName());
    }

    @Test
    void testEquals_ShouldReturnTrueWhenSameProduct() {
        Product product1 = new Product("1", "Product 1");
        Product product2 = new Product("1", "Product 1");

        assertEquals(product1, product2);
    }

    @Test
    void testEquals_ShouldReturnFalseWhenDifferentProductId() {
        Product product1 = new Product("1", "Product 1");
        Product product2 = new Product("2", "Product 2");

        assertNotEquals(product1, product2);
    }

    @Test
    void testEquals_ShouldReturnFalseWhenDifferentClass() {
        Product product = new Product("1", "Product 1");
        String nonProductObject = "Not a product";

        assertNotEquals(product, nonProductObject);
    }

    @Test
    void testHashCode_ShouldReturnSameHashCodeForEqualProducts() {
        Product product1 = new Product("1", "Product 1");
        Product product2 = new Product("1", "Product 1");

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void testHashCode_ShouldReturnDifferentHashCodeForDifferentProductIds() {
        Product product1 = new Product("1", "Product 1");
        Product product2 = new Product("2", "Product 2");

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

}