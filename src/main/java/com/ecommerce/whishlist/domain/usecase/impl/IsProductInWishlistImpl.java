package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.exception.WishlistNotFoundException;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.domain.usecase.IsProductInWishlist;

public class IsProductInWishlistImpl implements IsProductInWishlist {

    private final WishlistRepository wishlistRepository;

    public IsProductInWishlistImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public boolean isProductInWishlist(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));

        return wishlist.containsProduct(productId);
    }
}
