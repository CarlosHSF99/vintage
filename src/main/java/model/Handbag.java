package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

public class Handbag extends Product {
    private double dimension;
    private String material;
    private Year collectionYear;

    public Handbag(String seller, String description, String brand, String basePrice, int numberOfPreviousOwners, ShippingCompany shippingCompany, double dimension, String material, Year collectionYear) {
        super(seller, description, brand, basePrice, numberOfPreviousOwners, shippingCompany);
        this.dimension = dimension;
        this.material = material;
        this.collectionYear = collectionYear;
    }

    public Handbag(Handbag other) {
        super(other);
        this.dimension = other.dimension;
        this.material = other.material;
        this.collectionYear = other.collectionYear;
    }

    public double getDimension() {
        return dimension;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Year getCollectionYear() {
        return collectionYear;
    }

    public void setCollectionYear(Year collectionYear) {
        this.collectionYear = collectionYear;
    }

    @Override
    public BigDecimal priceCorrection() {
        return BigDecimal.ONE.divide(new BigDecimal(dimension), 2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal price() {
        return getBasePrice().multiply(BigDecimal.ONE.subtract(priceCorrection()));
    }

    @Override
    public String toString() {
        return "Handbag{" +
                super.toString() +
                ", dimension=" + dimension +
                ", material='" + material + '\'' +
                ", collectionYear=" + collectionYear +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Handbag handbag = (Handbag) o;

        if (Double.compare(handbag.dimension, dimension) != 0) return false;
        if (!material.equals(handbag.material)) return false;
        return collectionYear.equals(handbag.collectionYear);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(dimension);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + material.hashCode();
        result = 31 * result + collectionYear.hashCode();
        return result;
    }

    @Override
    public Handbag clone() {
        return new Handbag(this);
    }
}
