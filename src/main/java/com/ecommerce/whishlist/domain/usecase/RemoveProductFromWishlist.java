package com.ecommerce.whishlist.domain.usecase;

public interface RemoveProductFromWishlist {
    void removeProductFromWishlist(String customerId, String productId);
}
