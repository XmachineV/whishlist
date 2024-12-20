package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddProductToWishlistImplTest {
    private WishlistRepository wishlistRepository;
    private AddProductToWishlistImpl addProductToWishlist;

    @BeforeEach
    void setUp() {
        wishlistRepository = mock(WishlistRepository.class);
        addProductToWishlist = new AddProductToWishlistImpl(wishlistRepository);
    }

    @Test
    void shouldAddProductToExistingWishlist() {
        String customerId = "customer1";
        Product product = new Product("product1", "Product 1");
        Wishlist existingWishlist = new Wishlist(customerId);
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(existingWishlist));

        addProductToWishlist.addProductToWishlist(customerId, product);

        assertTrue(existingWishlist.containsProduct(product.getId()));
        verify(wishlistRepository).save(existingWishlist);
    }

    @Test
    void shouldCreateNewWishlistWhenNotFound() {
        String customerId = "customer2";
        Product product = new Product("product2", "Product 2");
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        addProductToWishlist.addProductToWishlist(customerId, product);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepository).save(wishlistCaptor.capture());
        Wishlist savedWishlist = wishlistCaptor.getValue();

        assertEquals(customerId, savedWishlist.getCustomerId());
        assertTrue(savedWishlist.containsProduct(product.getId()));
    }

    @Test
    void shouldNotAddDuplicateProduct() {
        String customerId = "customer1";
        Product product = new Product("product1", "Product 1");
        Wishlist existingWishlist = new Wishlist(customerId);
        existingWishlist.addProduct(product);
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(existingWishlist));

        addProductToWishlist.addProductToWishlist(customerId, product);

        assertEquals(1, existingWishlist.getAllProducts().size());
        verify(wishlistRepository).save(existingWishlist);
    }

    @Test
    void shouldThrowExceptionWhenLimitExceeded() {
        String customerId = "customer1";
        Wishlist existingWishlist = new Wishlist(customerId);
        for (int i = 0; i < 20; i++) {
            existingWishlist.addProduct(new Product("product" + i, "Product " + i));
        }
        Product newProduct = new Product("product21", "Product 21");
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(existingWishlist));

        assertThrows(RuntimeException.class, () -> addProductToWishlist.addProductToWishlist(customerId, newProduct));
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }
}