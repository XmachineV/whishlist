package com.ecommerce.whishlist.adapter.controller;


import com.ecommerce.whishlist.domain.model.Product;
import com.ecommerce.whishlist.domain.usecase.AddProductToWishlist;
import com.ecommerce.whishlist.domain.usecase.GetAllProductsFromWishlist;
import com.ecommerce.whishlist.domain.usecase.IsProductInWishlist;
import com.ecommerce.whishlist.domain.usecase.RemoveProductFromWishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WishlistControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private AddProductToWishlist addProductToWishlist;

    @Mock
    private RemoveProductFromWishlist removeProductFromWishlist;

    @Mock
    private IsProductInWishlist isProductInWishlist;

    @Mock
    private GetAllProductsFromWishlist getAllProductsFromWishlist;

    @InjectMocks
    private WishlistController wishlistController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(wishlistController).build();
    }

    @Test
    void testAddProductToWishlist() throws Exception {
        Product product = new Product("1", "Product 1");

        doNothing().when(addProductToWishlist).addProductToWishlist("customer1", product);

        mockMvc.perform(post("/api/wishlist/{customerId}/products", "customer1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"Product 1\"}"))
                .andExpect(status().isOk());

        verify(addProductToWishlist, times(1)).addProductToWishlist("customer1", product);
    }

    @Test
    void testRemoveProductFromWishlist() throws Exception {
        doNothing().when(removeProductFromWishlist).removeProductFromWishlist("customer1", "1");

        mockMvc.perform(delete("/api/wishlist/{customerId}/products/{productId}", "customer1", "1"))
                .andExpect(status().isNoContent());

        verify(removeProductFromWishlist, times(1)).removeProductFromWishlist("customer1", "1");
    }

    @Test
    void testIsProductInWishlist() throws Exception {
        when(isProductInWishlist.isProductInWishlist("customer1", "1")).thenReturn(true);

        mockMvc.perform(get("/api/wishlist/{customerId}/products/{productId}", "customer1", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(isProductInWishlist, times(1)).isProductInWishlist("customer1", "1");
    }

    @Test
    void testGetAllProductsFromWishlist() throws Exception {
        Product product1 = new Product("1", "Product 1");
        Product product2 = new Product("2", "Product 2");

        when(getAllProductsFromWishlist.getAllProductsFromWishlist("customer1")).thenReturn(List.of(product1, product2));

        mockMvc.perform(get("/api/wishlist/{customerId}/products", "customer1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));

        verify(getAllProductsFromWishlist, times(1)).getAllProductsFromWishlist("customer1");
    }

}