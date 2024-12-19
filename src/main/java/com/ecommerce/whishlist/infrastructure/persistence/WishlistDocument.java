package com.ecommerce.whishlist.infrastructure.persistence;

import com.ecommerce.whishlist.domain.model.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "wishlists")
public class WishlistDocument {
    @Id
    private String customerId;
    private List<Product> products;

    public WishlistDocument(String customerId, List<Product> products) {
        this.customerId = customerId;
        this.products = products;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<Product> getProducts() {
        return products;
    }
}
