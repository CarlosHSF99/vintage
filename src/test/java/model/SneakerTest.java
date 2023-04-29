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
        sneaker = new Sneaker("0AV7E", "description", "brand", "10.0", 0, Product.State.NEW_WITH_TAG, null, 38, Color.BLACK, true, Year.of(2020), "0.2");
        newSize40Sneaker = new Sneaker("", "", "", "10.0", 0, Product.State.NEW_WITH_TAG, null, 40, Color.BLACK, true, Year.now(), "0.2");;
        usedSize40Sneaker = new Sneaker("", "", "", "10.0", 1, Product.State.NEW_WITH_TAG, null, 40, Color.BLACK, true, Year.now(), "0.2");;;
        newSize50Sneaker = new Sneaker("", "", "", "10.0", 0, Product.State.NEW_WITH_TAG, null, 50, Color.BLACK, true, Year.now(), "0.2");;;
        usedSize50Sneaker = new Sneaker("", "", "", "10.0", 1, Product.State.NEW_WITH_TAG, null, 50, Color.BLACK, true, Year.now(), "0.2");;;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setAndGetSize() {
        int size = 40;
        sneaker.setSize(size);
        assertEquals(size, sneaker.getSize());
    }

    @Test
    void setAndGetColor() {
        Color color = Color.RED;
        sneaker.setColor(color);
        assertEquals(color, sneaker.getColor());
    }

    @Test
    void setAndHasLaces() {
        sneaker.setLaces(false);
        assertFalse(sneaker.hasLaces());
    }

    @Test
    void setAndGetCollectionYear() {
        Year year = Year.now();
        sneaker.setCollectionYear(year);
        assertEquals(year, sneaker.getCollectionYear());
    }

    @Test
    void setAndGetSellerPriceCorrection() {
        String discount = "0.25";
        sneaker.setSellerPriceCorrection(new BigDecimal(discount));
        assertEquals(0, new BigDecimal(discount).compareTo(sneaker.getSellerPriceCorrection()));
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