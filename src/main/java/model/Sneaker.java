package model;

import java.math.BigDecimal;
import java.time.Year;
import java.awt.Color;
import java.util.Objects;

/**
 * Sneaker class
 */
public class Sneaker extends Product {
    private int size;
    private Color color;
    private boolean laces;
    private Year collectionYear;
    private BigDecimal discount;

    /**
     * Parameterized constructor
     *
     * @param seller                 Seller identification code
     * @param description            Sneaker description
     * @param brand                  Sneaker brand
     * @param basePrice              Sneaker base price
     * @param numberOfPreviousOwners Sneaker number of previous owners
     * @param state                  Sneaker state
     * @param shippingCompany        Sneaker assigned shipping company
     * @param size                   Sneaker size
     * @param color                  Sneaker color
     * @param laces                  Sneaker laces
     * @param collectionYear         Sneaker collectionYear
     * @param discount               Sneaker discount
     */
    public Sneaker(String seller, String description, String brand, String basePrice, int numberOfPreviousOwners, int state, ShippingCompany shippingCompany, int size, Color color, boolean laces, Year collectionYear, BigDecimal discount) {
        super(seller, description, brand, basePrice, numberOfPreviousOwners, state, shippingCompany);
        this.size = size;
        this.color = color;
        this.laces = laces;
        this.collectionYear = collectionYear;
        this.discount = discount;
    }

    /**
     * Copy constructor
     *
     * @param other Other Sneaker
     */
    public Sneaker(Sneaker other) {
        super(other);
        this.size = other.size;
        this.color = other.color;
        this.laces = other.laces;
        this.collectionYear = other.collectionYear;
        this.discount = other.discount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean hasLaces() {
        return laces;
    }

    public void setLaces(boolean laces) {
        this.laces = laces;
    }

    public Year getCollectionYear() {
        return collectionYear;
    }

    public void setCollectionYear(Year collectionYear) {
        this.collectionYear = collectionYear;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal priceCorrection() {
        if (isUsed()) {
            // desconto de ser usado
        } else if (size > 45) { //nova mas tamanho > 45
            // desconto do tamanho
        } else return BigDecimal.ZERO;
    }

    /**
     * Returns the price of the Sneaker as a BigDecimal
     *
     * @return Price of Sneaker as a BigDecimal
     */
    @Override
    public BigDecimal price() {
        return getBasePrice().multiply(BigDecimal.ONE.subtract(priceCorrection()));
    }

    @Override
    public String toString() {
        return "Sneaker{" + super.toString() + ", size=" + size + ", color=" + color + ", laces=" + laces + ", collectionYear=" + collectionYear + ", discount=" + discount + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Sneaker sneaker = (Sneaker) o;

        if (size != sneaker.size) return false;
        if (laces != sneaker.laces) return false;
        if (!Objects.equals(color, sneaker.color)) return false;
        if (!Objects.equals(collectionYear, sneaker.collectionYear)) return false;
        return Objects.equals(discount, sneaker.discount);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + size;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (laces ? 1 : 0);
        result = 31 * result + (collectionYear != null ? collectionYear.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }

    @Override
    public Sneaker clone() {
        return new Sneaker(this);
    }
}

