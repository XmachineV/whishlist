package com.ecommerce.whishlist.infrastructure.repository;

import com.ecommerce.whishlist.domain.model.Wishlist;
import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.infrastructure.persistence.WishlistDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository {

    private final WishlistMongoRepository mongoRepository;

    @Autowired
    public WishlistRepositoryImpl(WishlistMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        WishlistDocument document = new WishlistDocument(
                wishlist.getCustomerId(),
                wishlist.getAllProducts()
        );
        mongoRepository.save(document);
        return wishlist;
    }

    @Override
    public Optional<Wishlist> findByCustomerId(String customerId) {
        return mongoRepository.findById(customerId)
                .map(doc -> {
                    Wishlist wishlist = new Wishlist(doc.getCustomerId());
                    doc.getProducts().forEach(wishlist::addProduct);
                    return wishlist;
                });
    }
}
