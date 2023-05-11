package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class SneakerTest {
    Sneaker sneaker;
    Sneaker newSize40Sneaker;
    Sneaker usedSize40Sneaker;
    Sneaker newSize50Sneaker;
    Sneaker usedSize50Sneaker;

    @BeforeEach
    void setUp() {
        sneaker = new Sneaker("0AV7E", "8RT21", "description", "brand", BigDecimal.valueOf(10), 0, Product.State.NEW_WITH_TAG, 38, Color.BLACK, true, Year.of(2020), "0.2");
        newSize40Sneaker = new Sneaker("", "", "", "", BigDecimal.valueOf(10), 0, Product.State.NEW_WITH_TAG, 40, Color.BLACK, true, Year.now(), "0.2");;
        usedSize40Sneaker = new Sneaker("", "", "", "", BigDecimal.valueOf(10), 1, Product.State.NEW_WITH_TAG, 40, Color.BLACK, true, Year.now(), "0.2");;;
        newSize50Sneaker = new Sneaker("", "", "", "", BigDecimal.valueOf(10), 0, Product.State.NEW_WITH_TAG, 50, Color.BLACK, true, Year.now(), "0.2");;;
        usedSize50Sneaker = new Sneaker("", "", "", "", BigDecimal.valueOf(10), 1, Product.State.NEW_WITH_TAG, 50, Color.BLACK, true, Year.now(), "0.2");;;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSize() {
        assertEquals(38, sneaker.getSize());
    }

    @Test
    void getColor() {
        assertEquals(Color.BLACK, sneaker.getColor());
    }

    @Test
    void hasLaces() {
        assertTrue(sneaker.hasLaces());
    }

    @Test
    void getCollectionYear() {
        assertEquals(Year.of(2020), sneaker.getCollectionYear());
    }

    @Test
    void setAndGetSellerPriceCorrection() {
        assertEquals(0, new BigDecimal("0.2").compareTo(sneaker.getSellerPriceCorrection()));
    }

    @Test
    void priceCorrectionOfNewSize40Sneaker() {
        assertEquals(0, BigDecimal.ONE.compareTo(newSize40Sneaker.priceCorrection()));
    }

    @Test
    void priceCorrectionOfUsedSize40Sneaker() {
        assertEquals(0, new BigDecimal("0.8").compareTo(usedSize40Sneaker.priceCorrection()));
    }

    @Test
    void priceCorrectionOfNewSize50Sneaker() {
        assertEquals(0, new BigDecimal("0.8").compareTo(newSize50Sneaker.priceCorrection()));
    }

    @Test
    void priceCorrectionOfUsedSize50Sneaker() {
        assertEquals(0, new BigDecimal("0.8").compareTo(usedSize50Sneaker.priceCorrection()));
    }
}