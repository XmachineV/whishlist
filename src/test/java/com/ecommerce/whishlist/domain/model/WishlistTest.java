package com.ecommerce.whishlist.domain.model;

import com.ecommerce.whishlist.domain.exception.WishlistProductLimitExceededException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WishlistTest {

    @Test
    void testAddProduct_ShouldAddProductWhenBelowLimit() {
        Wishlist wishlist = new Wishlist("customer1");
        Product product = new Product("product1", "Product 1");

        wishlist.addProduct(product);

        assertTrue(wishlist.containsProduct("product1"));
    }

    @Test
    void testAddProduct_ShouldThrowExceptionWhenLimitReached() {
        Wishlist wishlist = new Wishlist("customer1");

        for (int i = 0; i < 20; i++) {
            wishlist.addProduct(new Product("product" + i, "Product " + i));
        }

        Product newProduct = new Product("product21", "Product 21");

        assertThrows(WishlistProductLimitExceededException.class, () -> wishlist.addProduct(newProduct));
    }

    @Test
    void testAddProduct_ShouldNotAddDuplicateProduct() {
        Wishlist wishlist = new Wishlist("customer1");
        Product product = new Product("product1", "Product 1");

        wishlist.addProduct(product);
        wishlist.addProduct(product);

        assertEquals(1, wishlist.getAllProducts().size());
    }

    @Test
    void testRemoveProduct_ShouldRemoveProduct() {
        Wishlist wishlist = new Wishlist("customer1");
        Product product = new Product("product1", "Product 1");

        wishlist.addProduct(product);
        wishlist.removeProduct("product1");

        assertFalse(wishlist.containsProduct("product1"));
    }

    @Test
    void testGetAllProducts_ShouldReturnImmutableList() {
        Wishlist wishlist = new Wishlist("customer1");
        Product product = new Product("product1", "Product 1");

        wishlist.addProduct(product);

        assertThrows(UnsupportedOperationException.class, () -> wishlist.getAllProducts().clear());
    }
}
