package com.ecommerce.whishlist.domain.usecase;

import com.ecommerce.whishlist.domain.model.Product;

public interface AddProductToWishlist {
    void execute(String customerId, Product product);
}
