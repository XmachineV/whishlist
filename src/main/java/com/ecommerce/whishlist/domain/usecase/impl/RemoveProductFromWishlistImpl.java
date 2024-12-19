package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.exception.WishlistNotFoundException;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.domain.usecase.RemoveProductFromWishlist;

public class RemoveProductFromWishlistImpl implements RemoveProductFromWishlist {

    private final WishlistRepository wishlistRepository;

    public RemoveProductFromWishlistImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public void removeProductFromWishlist(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));

        wishlist.removeProduct(productId);
        wishlistRepository.save(wishlist);
    }
}
