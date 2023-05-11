package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

public class Handbag extends Product {
    private final double dimension;
    private final String material;
    private final Year collectionYear;

    public Handbag(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state, double dimension, String material, Year collectionYear) {
        super(sellerId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state);
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

    public String getMaterial() {
        return material;
    }

    public Year getCollectionYear() {
        return collectionYear;
    }

    @Override
    public BigDecimal priceCorrection() {
        var r = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(new BigDecimal(dimension), 2, RoundingMode.HALF_EVEN));
        if (r.compareTo(BigDecimal.valueOf(0.5)) < 0)
            r = new BigDecimal("0.5");
        return r;
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

    enum Material {
        CANVAS,
        COTTON,
        DENIM,
        FABRIC,
        LEATHER,
        NYLON,
        RAFFIA,
        STRAW,
        VEGAN_LEATHER,
        VELVET,
        VINYL,
    }
}
