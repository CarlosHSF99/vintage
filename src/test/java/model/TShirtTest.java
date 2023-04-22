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
        tshirt = new TShirt("seller", "description", "brand", "1.0", 0, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
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
        TShirt newPlainTShirt = new TShirt("", "", "", "1.0", 0, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
        assertEquals(0, new BigDecimal("0").compareTo(newPlainTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfUsedPlainShirt() {
        TShirt newPlainTShirt = new TShirt("", "", "", "1.0", 1, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
        assertEquals(0, new BigDecimal("0").compareTo(newPlainTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfNewStripesShirt() {
        TShirt newStripesTShirt = new TShirt("", "", "", "1.0", 0, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(0, new BigDecimal("0").compareTo(newStripesTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfUsedStripesShirt() {
        TShirt usedStripesTShirt = new TShirt("", "", "", "1.0", 1, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(0, new BigDecimal("0.5").compareTo(usedStripesTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfNewPalmTreesShirt() {
        TShirt newPalmTreesTShirt = new TShirt("", "", "", "1.0", 0, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(0, new BigDecimal("0").compareTo(newPalmTreesTShirt.priceCorrection()));
    }

    @Test
    void correctionPriceOfUsedPalmTreesShirt() {
        TShirt usedPalmTreesTShirt = new TShirt("", "", "", "1.0", 1, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(0, new BigDecimal("0.5").compareTo(usedPalmTreesTShirt.priceCorrection()));
    }

    @Test
    void priceOfNewPlainTShirt() {
        TShirt newPlainTShirt = new TShirt("", "", "", "1.0", 0, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
        assertEquals(0, new BigDecimal("1.0").compareTo(newPlainTShirt.price()));
    }

    @Test
    void priceOfUsedPlainTShirt() {
        TShirt usedPlainTShirt = new TShirt("", "", "", "1.0", 1, 0, false, null, Size.S, TShirt.Pattern.PLAIN);
        assertEquals(0, new BigDecimal("1.0").compareTo(usedPlainTShirt.price()));
    }

    @Test
    void priceOfNewStripesTShirt() {
        TShirt newStripesTShirt = new TShirt("", "", "", "1.0", 0, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(0, new BigDecimal("1.0").compareTo(newStripesTShirt.price()));
    }

    @Test
    void priceOfUsedStripesTShirt() {
        TShirt usedStripesTShirt = new TShirt("", "", "", "1.0", 1, 0, false, null, Size.S, TShirt.Pattern.STRIPES);
        assertEquals(0, new BigDecimal("0.5").compareTo(usedStripesTShirt.price()));
    }

    @Test
    void priceOfNewPalmTreesTShirt() {
        TShirt newPalmTreesTShirt = new TShirt("", "", "", "1.0", 0, 0, false, null, Size.S, TShirt.Pattern.PALM_TREES);
        assertEquals(0, new BigDecimal("1.0").compareTo(newPalmTreesTShirt.price()));
    }

    @Test
    void priceOfUsedPalmTreesTShirt() {
        TShirt usedPalmTreesTShirt = new TShirt("", "", "", "1.0", 1, 0, false, null, Size.S, TShirt.Pattern.PALM_TREES);
        assertEquals(0, new BigDecimal("0.5").compareTo(usedPalmTreesTShirt.price()));
    }
}
