package com.ecommerce.whishlist.domain.exception;

public class WishlistProductLimitExceededException extends RuntimeException {
    public WishlistProductLimitExceededException(int maxProducts) {
        super("Wishlist cannot have more than " + maxProducts + " products.");
    }
}
