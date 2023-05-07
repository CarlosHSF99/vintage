package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class PremiumSneakerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void priceCorrection() {
        PremiumSneaker premiumSneaker = new PremiumSneaker("", "", "", "", BigDecimal.valueOf(10), 0, Product.State.GOOD, 38, Color.BLACK, true, Year.of(2019), "0.2");
        assertEquals(0, new BigDecimal("1.2").pow(4).compareTo(premiumSneaker.priceCorrection()));
    }
}