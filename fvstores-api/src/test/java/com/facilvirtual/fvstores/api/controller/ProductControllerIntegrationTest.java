package com.facilvirtual.fvstores.api.controller;

import com.facilvirtual.fvstores.api.FvStoresApiApplication;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FvStoresApiApplication.class)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON strings if needed for assertions

    @Test
    public void testGetProductById_Found() throws Exception {
        Long testProductId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(testProductId);
        mockProduct.setBarCode("12345");
        mockProduct.setDescription("Test Product");
        mockProduct.setSellingPrice(10.99);

        when(productService.findById(testProductId)).thenReturn(mockProduct);

        mockMvc.perform(get("/api/products/{id}", testProductId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testProductId.intValue())))
                .andExpect(jsonPath("$.barCode", is("12345")))
                .andExpect(jsonPath("$.description", is("Test Product")))
                .andExpect(jsonPath("$.sellingPrice", is(10.99)));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        Long testProductId = 2L;
        when(productService.findById(testProductId)).thenReturn(null);

        mockMvc.perform(get("/api/products/{id}", testProductId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setBarCode("P001");
        product1.setDescription("Product 1");
        product1.setSellingPrice(10.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setBarCode("P002");
        product2.setDescription("Product 2");
        product2.setSellingPrice(20.0);

        List<Product> mockProducts = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(mockProducts);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].barCode", is("P001")))
                .andExpect(jsonPath("$[0].description", is("Product 1")))
                .andExpect(jsonPath("$[1].barCode", is("P002")))
                .andExpect(jsonPath("$[1].description", is("Product 2")));
    }
}
