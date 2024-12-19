package com.ecommerce.whishlist.domain.repository;

import com.ecommerce.whishlist.domain.model.Wishlist;
import java.util.Optional;

public interface WishlistRepository {
    Wishlist save(Wishlist wishlist);
    Optional<Wishlist> findByCustomerId(String customerId);
}
