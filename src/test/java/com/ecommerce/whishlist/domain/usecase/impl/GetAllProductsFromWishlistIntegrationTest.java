package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.exception.WishlistNotFoundException;
import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.domain.usecase.GetAllProductsFromWishlist;
import com.ecommerce.whishlist.infrastructure.repository.WishlistMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class GetAllProductsFromWishlistIntegrationTest {

    @Autowired
    private WishlistMongoRepository wishlistMongoRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private GetAllProductsFromWishlist getAllProductsFromWishlist;

    @BeforeEach
    public void setUp() {
        wishlistMongoRepository.deleteAll();
    }

    @Test
    void testGetAllProductsFromWishlist_shouldReturnAllProductsFromExistingWishlist() {
        String customerId = "customer1";
        Product product1 = new Product("1", "Product 1");
        Product product2 = new Product("2", "Product 2");

        Wishlist wishlist = new Wishlist(customerId);
        wishlist.addProduct(product1);
        wishlist.addProduct(product2);
        wishlistRepository.save(wishlist);

        List<Product> products = getAllProductsFromWishlist.getAllProductsFromWishlist(customerId);

        assertThat(products).hasSize(2);
        assertThat(products).containsExactlyInAnyOrder(product1, product2);
    }

    @Test
    void testGetAllProductsFromWishlist_shouldThrowExceptionIfWishlistNotFound() {
        String nonExistentCustomerId = "nonexistent-customer";

        assertThatThrownBy(() -> getAllProductsFromWishlist.getAllProductsFromWishlist(nonExistentCustomerId))
                .isInstanceOf(WishlistNotFoundException.class)
                .hasMessageContaining(nonExistentCustomerId);
    }
}
