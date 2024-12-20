package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.domain.usecase.AddProductToWishlist;
import com.ecommerce.whishlist.infrastructure.repository.WishlistMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddProductToWishlistIntegrationTest {

    @Autowired
    private WishlistMongoRepository wishlistMongoRepository;

    @Autowired
    private WishlistRepository wishlistRepository;


    @Autowired
    private AddProductToWishlist addProductToWishlist;

    @BeforeEach
    public void setUp() {
        wishlistMongoRepository.deleteAll();
    }

    @Test
    void testAddProductToWishlist_shouldAddProductToExistingWishlist() {
        String customerId = "customer1";
        Product product = new Product("1", "Product 1");

        Wishlist wishlist = new Wishlist(customerId);
        wishlistRepository.save(wishlist);

        addProductToWishlist.addProductToWishlist(customerId, product);

        Wishlist savedWishlist = wishlistRepository.findByCustomerId(customerId).orElse(null);
        assertThat(savedWishlist).isNotNull();
        assertThat(savedWishlist.getAllProducts()).hasSize(1);
        assertThat(savedWishlist.getAllProducts().get(0)).isEqualTo(product);
    }

    @Test
    void testAddProductToWishlist_shouldCreateNewWishlistIfNoneExists() {
        String customerId = "customer2";
        Product product = new Product("2", "Product 2");

        addProductToWishlist.addProductToWishlist(customerId, product);

        Wishlist savedWishlist = wishlistRepository.findByCustomerId(customerId).orElse(null);
        assertThat(savedWishlist).isNotNull();
        assertThat(savedWishlist.getAllProducts()).hasSize(1);
        assertThat(savedWishlist.getAllProducts().get(0)).isEqualTo(product);
    }
}
