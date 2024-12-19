package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.exception.WishlistNotFoundException;
import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.domain.usecase.GetAllProductsFromWishlist;

import java.util.List;

public class GetAllProductsFromWishlistImpl implements GetAllProductsFromWishlist {

    private final WishlistRepository wishlistRepository;

    public GetAllProductsFromWishlistImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public List<Product> getAllProductsFromWishlist(String customerId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));

        return wishlist.getAllProducts();
    }
}
