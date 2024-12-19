package com.ecommerce.whishlist.domain.usecase;

public interface IsProductInWishlist {
    boolean isProductInWishlist(String customerId, String productId);
}
