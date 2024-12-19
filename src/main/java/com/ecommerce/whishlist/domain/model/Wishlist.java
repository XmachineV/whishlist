package com.ecommerce.whishlist.domain.model;

import com.ecommerce.whishlist.domain.exception.WishlistProductLimitExceededException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wishlist {
    private String customerId;
    private List<Product> products;
    private static final int MAX_PRODUCTS = 20;

    public Wishlist(String customerId) {
        this.customerId = customerId;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (products.size() >= MAX_PRODUCTS) {
            throw new WishlistProductLimitExceededException(MAX_PRODUCTS);
        }
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    public void removeProduct(String productId) {
        products.removeIf(product -> product.getId().equals(productId));
    }

    public boolean containsProduct(String productId) {
        return products.stream().anyMatch(product -> product.getId().equals(productId));
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public String getCustomerId() {
        return customerId;
    }

}
