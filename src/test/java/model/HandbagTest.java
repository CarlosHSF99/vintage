package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class HandbagTest {
    Handbag handbag;

    @BeforeEach
    void setUp() {
        handbag = new Handbag("seller", "shippingCompany", "description", "brand", BigDecimal.ONE, 0, Product.State.GOOD, 4.0, "stone", Year.of(2020));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDimension() {
        assertEquals(4.0, handbag.getDimension());
    }

    @Test
    void getMaterial() {
    }

    @Test
    void setMaterial() {
    }

    @Test
    void getCollectionYear() {
        assertEquals(Year.of(2020), handbag.getCollectionYear());
    }

    @Test
    void priceCorrection() {
        assertEquals(0, new BigDecimal("0.75").compareTo(handbag.priceCorrection()));
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testClone() {
    }
}