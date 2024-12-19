package com.ecommerce.whishlist.adapter.controller;

import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.usecase.AddProductToWishlist;
import com.ecommerce.whishlist.domain.usecase.GetAllProductsFromWishlist;
import com.ecommerce.whishlist.domain.usecase.IsProductInWishlist;
import com.ecommerce.whishlist.domain.usecase.RemoveProductFromWishlist;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final AddProductToWishlist addProductToWishlist;

    private final RemoveProductFromWishlist removeProductFromWishlist;

    private final IsProductInWishlist isProductInWishlist;

    private final GetAllProductsFromWishlist getAllProductsFromWishlist;

    public WishlistController(AddProductToWishlist addProductToWishlist,
                              RemoveProductFromWishlist removeProductFromWishlist,
                              IsProductInWishlist isProductInWishlist,
                              GetAllProductsFromWishlist getAllProductsFromWishlist) {
        this.addProductToWishlist = addProductToWishlist;
        this.removeProductFromWishlist = removeProductFromWishlist;
        this.isProductInWishlist = isProductInWishlist;
        this.getAllProductsFromWishlist = getAllProductsFromWishlist;
    }

    @Operation(summary = "Add a product to the wishlist")
    @PostMapping("/{customerId}/products")
    public ResponseEntity<Void> addProductToWishlist(
            @PathVariable String customerId,
            @RequestBody Product product) {
        addProductToWishlist.execute(customerId, product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Remove a product to the wishlist")
    @DeleteMapping("/{customerId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(
            @PathVariable String customerId,
            @PathVariable String productId) {
        removeProductFromWishlist.removeProductFromWishlist(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if a product is in the wishlist")
    @GetMapping("/{customerId}/products/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(
            @PathVariable String customerId,
            @PathVariable String productId) {
        boolean isInWishlist = isProductInWishlist.isProductInWishlist(customerId, productId);
        return ResponseEntity.ok(isInWishlist);
    }

    @Operation(summary = "Get all products from the wishlist")
    @GetMapping("/{customerId}/products")
    public ResponseEntity<List<Product>> getAllProductsFromWishlist(@PathVariable String customerId) {
        List<Product> products = getAllProductsFromWishlist.getAllProductsFromWishlist(customerId);
        return ResponseEntity.ok(products);
    }
}
