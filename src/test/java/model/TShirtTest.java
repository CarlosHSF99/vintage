package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TShirtTest {
    TShirt tshirt;

    @BeforeEach
    void setUp() {
        tshirt = new TShirt("seller", "description", "brand", "1", "0.25", 0, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setAndGetSize() {
        tshirt.setSize(Size.S);
        assertEquals(Size.S, tshirt.getSize());
        tshirt.setSize(Size.M);
        assertEquals(Size.M, tshirt.getSize());
        tshirt.setSize(Size.L);
        assertEquals(Size.L, tshirt.getSize());
        tshirt.setSize(Size.XL);
        assertEquals(Size.XL, tshirt.getSize());
    }

    @Test
    void setAndGetPattern() {
        tshirt.setPattern(TShirt.Pattern.PLAIN);
        assertEquals(TShirt.Pattern.PLAIN, tshirt.getPattern());
        tshirt.setPattern(TShirt.Pattern.STRIPES);
        assertEquals(TShirt.Pattern.STRIPES, tshirt.getPattern());
        tshirt.setPattern(TShirt.Pattern.PALM_TREES);
        assertEquals(TShirt.Pattern.PALM_TREES, tshirt.getPattern());
    }

    @Test
    void priceOfNewPlainTShirt() {
        TShirt newPlainTshirt = new TShirt("", "", "", "1", "0.25", 0, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
        assertEquals(new BigDecimal("0.75"), newPlainTshirt.price());
    }

    @Test
    void priceOfUsedPlainTShirt() {
        TShirt usedPlainTShirt = new TShirt("", "", "", "1", "0.25", 1, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
        assertEquals(new BigDecimal("0.75"), usedPlainTShirt.price());
    }

    @Test
    void priceOfNewStripesTShirt() {
        TShirt newStripesTShirt = new TShirt("", "", "", "1", "0.25", 0, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(new BigDecimal("0.75"), newStripesTShirt.price());
    }

    @Test
    void priceOfUsedStripesTShirt() {
        TShirt usedStripesTShirt = new TShirt("", "", "", "1", "0.25", 1, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(new BigDecimal("0.25"), usedStripesTShirt.price());
    }

    @Test
    void priceOfNewPalmTreesTShirt() {
        TShirt newPalmTreesTShirt = new TShirt("", "", "", "1", "0.25", 0, 0, false, null, Size.S, TShirt.Pattern.PALM_TREES);
        assertEquals(new BigDecimal("0.75"), newPalmTreesTShirt.price());
    }

    @Test
    void priceOfUsedPalmTreesTShirt() {
        TShirt usedPalmTreesTShirt = new TShirt("", "", "", "1", "0.25", 1, 0, false, null, Size.S, TShirt.Pattern.PALM_TREES);
        assertEquals(new BigDecimal("0.25"), usedPalmTreesTShirt.price());
    }

    @Test
    void priceWhenDiscountGreaterThan1() {
        TShirt fullyDiscountedTShirt = new TShirt("", "", "", "1", "0.60", 1, 0, false, null, Size.S, TShirt.Pattern.PALM_TREES);
        assertEquals(new BigDecimal("0"), fullyDiscountedTShirt.price());
    }
}
