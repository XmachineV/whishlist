package com.ecommerce.whishlist.domain.usecase;

import com.ecommerce.whishlist.domain.model.Product;

import java.util.List;

public interface GetAllProductsFromWishlist {

    List<Product> getAllProductsFromWishlist(String customerId);
}
