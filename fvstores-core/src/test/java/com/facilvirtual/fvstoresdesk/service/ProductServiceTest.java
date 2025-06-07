package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.ProductDao;
import com.facilvirtual.fvstoresdesk.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    void testFindById_Found() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setDescription("Test Product");

        // In ProductDao, findById returns Product, not Optional<Product>
        // So, mock productDao.findById(productId) to return the mockProduct
        when(productDao.findById(productId)).thenReturn(mockProduct);

        // Act
        Product foundProduct = productService.findById(productId);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals("Test Product", foundProduct.getDescription());
        verify(productDao).findById(productId);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        Long productId = 2L;
        when(productDao.findById(productId)).thenReturn(null); // DAO returns null if not found

        // Act
        Product foundProduct = productService.findById(productId);

        // Assert
        assertNull(foundProduct);
        verify(productDao).findById(productId);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> mockProductList = new ArrayList<>();
        Product p1 = new Product(); p1.setId(1L); p1.setDescription("P1");
        Product p2 = new Product(); p2.setId(2L); p2.setDescription("P2");
        mockProductList.add(p1);
        mockProductList.add(p2);

        when(productDao.getAllProducts()).thenReturn(mockProductList);

        // Act
        List<Product> resultProducts = productService.getAllProducts();

        // Assert
        assertNotNull(resultProducts);
        assertEquals(2, resultProducts.size());
        assertEquals("P1", resultProducts.get(0).getDescription());
        verify(productDao).getAllProducts();
    }
}
