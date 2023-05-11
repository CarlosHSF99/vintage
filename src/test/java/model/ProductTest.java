package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        product = new Product("H45DF2", "0FA7", "description", "brand", BigDecimal.valueOf(10), 1, Product.State.GOOD) {
            @Override
            public BigDecimal priceCorrection() { return null; }
            @Override
            public BigDecimal price() { return null; }
            @Override
            public Product clone() { return null; }
        };
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSellerId() {
        assertEquals("H45DF2", product.getSellerId());
    }

    @Test
    void getShippingCompanyId() {
        assertEquals("0FA7", product.getShippingCompanyId());
    }

    @Test
    void getDescription() {
        assertEquals("description", product.getDescription());
    }

    @Test
    void getBrand() {
        assertEquals("brand", product.getBrand());
    }

    @Test
    void getBasePrice() {
        assertEquals(0, BigDecimal.valueOf(10).compareTo(product.getBasePrice()));
    }

    @Test
    void getNumberOfPreviousOwners() {
        assertEquals(1, product.getNumberOfPreviousOwners());
    }

    @Test
    void getState() {
        assertEquals(Product.State.GOOD, product.getState());
    }

    @Test
    void isNew() {
        assertFalse(product.isNew());
    }

    @Test
    void isUsed() {
        assertTrue(product.isUsed());
    }
}
