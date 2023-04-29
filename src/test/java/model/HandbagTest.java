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
        handbag = new Handbag("seller", "description", "brand", "1", 0, null, 1.0, "stone", Year.of(2020));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setAndGetDimension() {
        var dimension = 10.0;
        handbag.setDimension(dimension);
        assertEquals(dimension, handbag.getDimension());
    }

    @Test
    void getMaterial() {
    }

    @Test
    void setMaterial() {
    }

    @Test
    void setAndGetCollectionYear() {
        Year year = Year.of(2016);
        handbag.setCollectionYear(year);
        assertEquals(year, handbag.getCollectionYear());
    }

    @Test
    void priceCorrection() {
        handbag.setBasePrice("10.0");
        handbag.setDimension(2.0);
        assertEquals(0, new BigDecimal("5.0").compareTo(handbag.priceCorrection()));
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