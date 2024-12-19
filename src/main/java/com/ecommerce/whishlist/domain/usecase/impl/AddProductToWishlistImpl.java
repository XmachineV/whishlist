package com.ecommerce.whishlist.domain.usecase.impl;

import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.domain.usecase.AddProductToWishlist;

public class AddProductToWishlistImpl implements AddProductToWishlist {

    private final WishlistRepository wishlistRepository;


    public AddProductToWishlistImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public void execute(String customerId, Product product) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElse(new Wishlist(customerId));

        wishlist.addProduct(product);
        wishlistRepository.save(wishlist);
    }
}
