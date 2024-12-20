package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.exception.WishlistNotFoundException;
import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IsProductInWishlistImplTest {

    private WishlistRepository wishlistRepository;
    private IsProductInWishlistImpl isProductInWishlist;

    @BeforeEach
    void setup() {
        wishlistRepository = mock(WishlistRepository.class);
        isProductInWishlist = new IsProductInWishlistImpl(wishlistRepository);
    }

    @Test
    void shouldReturnTrue_WhenProductIsInWishlist() {
        String customerId = "customer1";
        String productId = "product1";
        Wishlist wishlist = new Wishlist(customerId);
        Product product = new Product(productId, "Product 1");
        wishlist.addProduct(product);

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        boolean result = isProductInWishlist.isProductInWishlist(customerId, productId);

        assertTrue(result);
        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void shouldReturnFalse_WhenProductIsNotInWishlist() {
        String customerId = "customer1";
        String productId = "product1";
        Wishlist wishlist = new Wishlist(customerId);

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        boolean result = isProductInWishlist.isProductInWishlist(customerId, productId);

        assertFalse(result);
        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void shouldThrowWishlistNotFoundException_WhenWishlistDoesNotExist() {
        String customerId = "customer1";
        String productId = "product1";

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        assertThrows(WishlistNotFoundException.class,
                () -> isProductInWishlist.isProductInWishlist(customerId, productId));

        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
    }
}
