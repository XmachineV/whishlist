package com.ecommerce.whishlist.infrastructure.configuration;

import com.ecommerce.whishlist.domain.repository.WishlistRepository;
import com.ecommerce.whishlist.domain.usecase.AddProductToWishlist;
import com.ecommerce.whishlist.domain.usecase.GetAllProductsFromWishlist;
import com.ecommerce.whishlist.domain.usecase.IsProductInWishlist;
import com.ecommerce.whishlist.domain.usecase.RemoveProductFromWishlist;
import com.ecommerce.whishlist.domain.usecase.impl.AddProductToWishlistImpl;
import com.ecommerce.whishlist.domain.usecase.impl.GetAllProductsFromWishlistImpl;
import com.ecommerce.whishlist.domain.usecase.impl.IsProductInWishlistImpl;
import com.ecommerce.whishlist.domain.usecase.impl.RemoveProductFromWishlistImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public AddProductToWishlist addProductToWishlist(WishlistRepository wishlistRepository) {
        return new AddProductToWishlistImpl(wishlistRepository);
    }

    @Bean
    public RemoveProductFromWishlist removeProductFromWishlist(WishlistRepository wishlistRepository) {
        return new RemoveProductFromWishlistImpl(wishlistRepository);
    }

    @Bean
    public IsProductInWishlist isProductInWishlist(WishlistRepository wishlistRepository) {
        return new IsProductInWishlistImpl(wishlistRepository);
    }

    @Bean
    public GetAllProductsFromWishlist getAllProductsFromWishlist(WishlistRepository wishlistRepository) {
        return new GetAllProductsFromWishlistImpl(wishlistRepository);
    }

}
