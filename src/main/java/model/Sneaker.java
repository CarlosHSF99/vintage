package model;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Year;

/**
 * Sneaker class
 */
public class Sneaker extends Product implements Serializable {
    private final int size;
    private final Color color;
    private final boolean laces;
    private final Year collectionYear;
    private final BigDecimal sellerPriceCorrection;

    /**
     * Parameterized constructor
     *
     * @param sellerId               Seller identification code
     * @param shippingCompanyId      Sneaker assigned shipping company id
     * @param description            Sneaker description
     * @param brand                  Sneaker brand
     * @param basePrice              Sneaker base price
     * @param numberOfPreviousOwners Sneaker number of previous owners
     * @param state                  Sneaker state
     * @param size                   Sneaker size
     * @param color                  Sneaker color
     * @param laces                  Sneaker laces
     * @param collectionYear         Sneaker collection year
     * @param discount               Sneaker discount defined by seller
     */
    public Sneaker(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state, int size, Color color, boolean laces, Year collectionYear, BigDecimal discount) {
        super(sellerId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state);
        this.size = size;
        this.color = color;
        this.laces = laces;
        this.collectionYear = collectionYear;
        this.sellerPriceCorrection = discount;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasLaces() {
        return laces;
    }

    public Year getCollectionYear() {
        return collectionYear;
    }

    public BigDecimal getSellerPriceCorrection() {
        return sellerPriceCorrection;
    }

    /**
     * Returns the price correction.
     * Price correction is the discount value defined by the user applied when the sneaker is used or is has a size bigger than 45.
     *
     * @return price correction
     */
    public BigDecimal priceCorrection() {
        return isUsed() || size > 45 ? BigDecimal.ONE.subtract(sellerPriceCorrection) : BigDecimal.ONE;
    }

    @Override
    public String show() {
        return "Sneaker, " + super.show() + ", Size: " + size + ", Color: " + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()) + ", " + (laces ? "has" : "no") + " laces, Collection year: " + collectionYear;
    }

    @Override
    public String toString() {
        return "Sneaker{" +
                super.toString() +
                ", size=" + size +
                ", color=" + color +
                ", laces=" + laces +
                ", collectionYear=" + collectionYear +
                ", sellerPriceCorrection=" + sellerPriceCorrection +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Sneaker sneaker = (Sneaker) o;

        if (size != sneaker.size) return false;
        if (laces != sneaker.laces) return false;
        if (!color.equals(sneaker.color)) return false;
        if (!collectionYear.equals(sneaker.collectionYear)) return false;
        return sellerPriceCorrection.equals(sneaker.sellerPriceCorrection);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + size;
        result = 31 * result + color.hashCode();
        result = 31 * result + (laces ? 1 : 0);
        result = 31 * result + collectionYear.hashCode();
        result = 31 * result + sellerPriceCorrection.hashCode();
        return result;
    }
}
