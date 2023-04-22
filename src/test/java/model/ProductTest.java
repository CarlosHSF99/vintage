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
        product = new Product("seller", "", "", "1.0", "0.25", 0, 0, false, null) {
            @Override
            public BigDecimal price() {
                return null;
            }

            @Override
            public Product clone() {
                return null;
            }
        };
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCode() {
    }

    void getSeller() {
        assertEquals("seller", product.getSeller());
    }

    @Test
    void setAndGetDescription() {
        var description = "description";
        product.setDescription(description);
        assertEquals(description, product.getDescription());
    }

    @Test
    void setAndGetBrand() {
        var brand = "brand";
        product.setBrand(brand);
        assertEquals(brand, product.getBrand());
    }

    @Test
    void setAndGetBasePrice() {
        var basePrice = "1.0";
        product.setBasePrice(basePrice);
        assertEquals(new BigDecimal(basePrice), product.getBasePrice());
    }

    @Test
    void setAndGetPriceCorrection() {
        var priceCorrection = "0.25";
        product.setBrand(priceCorrection);
        assertEquals(new BigDecimal(priceCorrection), product.getPriceCorrection());
    }

    @Test
    void setGetAndIncrementNumberOfPreviousOwners() {
        var numberOfPreviousOwners = 42;
        product.setNumberOfPreviousOwners(numberOfPreviousOwners);
        assertEquals(numberOfPreviousOwners, product.getNumberOfPreviousOwners());
        product.incrementNumberOfPreviousOwners();
        assertEquals(numberOfPreviousOwners + 1, product.getNumberOfPreviousOwners());
    }

    @Test
    void setAndGetState() {
        var state = 5;
        product.setState(state);
        assertEquals(state, product.getState());
    }

    @Test
    void setAndIsPremium() {
        product.setPremium(false);
        assertFalse(product.isPremium());
        product.setPremium(true);
        assertTrue(product.isPremium());
    }

    @Test
    void setAndGetShippingCompany() {
        var shippingCompany = new ShippingCompany();
        product.setShippingCompany(shippingCompany);
        assertEquals(shippingCompany, product.getShippingCompany());
    }

    @Test
    void isNew() {
        product.setNumberOfPreviousOwners(0);
        assertTrue(product.isNew());
        product.setNumberOfPreviousOwners(1);
        assertFalse(product.isNew());
    }

    @Test
    void isUsed() {
        product.setNumberOfPreviousOwners(0);
        assertFalse(product.isUsed());
        product.setNumberOfPreviousOwners(1);
        assertTrue(product.isUsed());
    }
}
