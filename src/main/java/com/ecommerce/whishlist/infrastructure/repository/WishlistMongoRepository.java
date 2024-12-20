package com.ecommerce.whishlist.infrastructure.repository;

import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.infrastructure.persistence.WishlistDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistMongoRepository extends MongoRepository<WishlistDocument, String> {
    Wishlist save(Wishlist wishlist);

    Optional<Wishlist> findByCustomerId(String customerId);

}
