package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TShirtTest {
    TShirt tshirt;
    TShirt newPlainTShirt;
    TShirt usedPlainTShirt;
    TShirt newStripesTShirt;
    TShirt usedStripesTShirt;
    TShirt newPalmTreesTShirt;
    TShirt usedPalmTreesTShirt;

    @BeforeEach
    void setUp() {
        tshirt = new TShirt("seller", "shippingCompany", "description", "brand", BigDecimal.valueOf(1), 0, Product.State.GOOD, Size.S, TShirt.Pattern.PLAIN);
        newPlainTShirt = new TShirt("", "", "", "", BigDecimal.valueOf(1), 0, Product.State.GOOD, Size.S, TShirt.Pattern.PLAIN);
        usedPlainTShirt = new TShirt("", "", "", "", BigDecimal.valueOf(1), 1, Product.State.GOOD, Size.S, TShirt.Pattern.PLAIN);
        newStripesTShirt = new TShirt("", "", "", "", BigDecimal.valueOf(1), 0, Product.State.GOOD, Size.S, TShirt.Pattern.STRIPES);
        usedStripesTShirt = new TShirt("", "", "", "", BigDecimal.valueOf(1), 1, Product.State.GOOD, Size.S, TShirt.Pattern.STRIPES);
        newPalmTreesTShirt = new TShirt("", "", "", "", BigDecimal.valueOf(1), 0, Product.State.GOOD, Size.S, TShirt.Pattern.PALM_TREES);
        usedPalmTreesTShirt = new TShirt("", "", "", "", BigDecimal.valueOf(1), 1, Product.State.GOOD, Size.S, TShirt.Pattern.PALM_TREES);
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
    void correctionPriceOfNewPlainShirt() {
        assertEquals(0, new BigDecimal("1").compareTo(newPlainTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfUsedPlainShirt() {
        assertEquals(0, new BigDecimal("1").compareTo(usedPlainTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfNewStripesShirt() {
        assertEquals(0, new BigDecimal("1").compareTo(newStripesTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfUsedStripesShirt() {
        assertEquals(0, new BigDecimal("0.5").compareTo(usedStripesTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfNewPalmTreesShirt() {
        assertEquals(0, new BigDecimal("1").compareTo(newPalmTreesTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfUsedPalmTreesShirt() {
        assertEquals(0, new BigDecimal("0.5").compareTo(usedPalmTreesTShirt.priceCorrection()));
    }

    @Test
    void priceOfNewPlainTShirt() {
        assertEquals(0, new BigDecimal("1.0").compareTo(newPlainTShirt.price()));
    }

    @Test
    void priceOfUsedPlainTShirt() {
        assertEquals(0, new BigDecimal("1.0").compareTo(usedPlainTShirt.price()));
    }

    @Test
    void priceOfNewStripesTShirt() {
        assertEquals(0, new BigDecimal("1.0").compareTo(newStripesTShirt.price()));
    }

    @Test
    void priceOfUsedStripesTShirt() {
        assertEquals(0, new BigDecimal("0.5").compareTo(usedStripesTShirt.price()));
    }

    @Test
    void priceOfNewPalmTreesTShirt() {
        assertEquals(0, new BigDecimal("1.0").compareTo(newPalmTreesTShirt.price()));
    }

    @Test
    void priceOfUsedPalmTreesTShirt() {
        assertEquals(0, new BigDecimal("0.5").compareTo(usedPalmTreesTShirt.price()));
    }
}
