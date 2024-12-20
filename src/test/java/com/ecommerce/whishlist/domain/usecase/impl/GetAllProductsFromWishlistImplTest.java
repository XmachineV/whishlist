package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.exception.WishlistNotFoundException;
import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllProductsFromWishlistImplTest {

    private WishlistRepository wishlistRepository;
    private GetAllProductsFromWishlistImpl getAllProductsFromWishlist;

    @BeforeEach
    void setup() {
        wishlistRepository = mock(WishlistRepository.class);
        getAllProductsFromWishlist = new GetAllProductsFromWishlistImpl(wishlistRepository);
    }

    @Test
    void shouldReturnAllProductsFromWishlist_WhenWishlistExists() {
        String customerId = "customer1";
        Wishlist wishlist = new Wishlist(customerId);
        Product product1 = new Product("1", "Product 1");
        Product product2 = new Product("2", "Product 2");
        wishlist.addProduct(product1);
        wishlist.addProduct(product2);

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        List<Product> products = getAllProductsFromWishlist.getAllProductsFromWishlist(customerId);

        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void shouldThrowWishlistNotFoundException_WhenWishlistDoesNotExist() {
        String customerId = "customer1";
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        assertThrows(WishlistNotFoundException.class,
                () -> getAllProductsFromWishlist.getAllProductsFromWishlist(customerId));

        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void shouldReturnEmptyList_WhenWishlistHasNoProducts() {
        String customerId = "customer1";
        Wishlist wishlist = new Wishlist(customerId);

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        List<Product> products = getAllProductsFromWishlist.getAllProductsFromWishlist(customerId);

        assertTrue(products.isEmpty());
        verify(wishlistRepository, times(1)).findByCustomerId(customerId);
    }
}
