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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RemoveProductFromWishlistImplTest {

    private WishlistRepository wishlistRepository;
    private RemoveProductFromWishlistImpl removeProductFromWishlist;

    @BeforeEach
    void setup() {
        wishlistRepository = mock(WishlistRepository.class);
        removeProductFromWishlist = new RemoveProductFromWishlistImpl(wishlistRepository);
    }

    @Test
    void shouldRemoveProduct_WhenProductExistsInWishlist() {
        String customerId = "customer1";
        String productId = "product1";
        Wishlist wishlist = new Wishlist(customerId);
        Product product = new Product(productId, "Product 1");
        wishlist.addProduct(product);

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        removeProductFromWishlist.removeProductFromWishlist(customerId, productId);

        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
        verify(wishlistRepository, times(1)).save(wishlist);
        assertFalse(wishlist.containsProduct(productId));
    }

    @Test
    void shouldDoNothing_WhenProductDoesNotExistInWishlist() {
        String customerId = "customer1";
        String productId = "product1";
        Wishlist wishlist = new Wishlist(customerId);

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        removeProductFromWishlist.removeProductFromWishlist(customerId, productId);

        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
        verify(wishlistRepository, times(1)).save(wishlist);
        assertFalse(wishlist.containsProduct(productId));
    }

    @Test
    void shouldThrowWishlistNotFoundException_WhenWishlistDoesNotExist() {
        String customerId = "customer1";
        String productId = "product1";

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        assertThrows(WishlistNotFoundException.class,
                () -> removeProductFromWishlist.removeProductFromWishlist(customerId, productId));

        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }
}
