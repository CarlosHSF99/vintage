package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

public class Handbag extends Product implements Serializable {
    private final BigDecimal dimension;
    private final Material material;
    private final Year collectionYear;

    public Handbag(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state, BigDecimal dimension, Material material, Year collectionYear) {
        super(sellerId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state);
        this.dimension = dimension;
        this.material = material;
        this.collectionYear = collectionYear;
    }

    public BigDecimal getDimension() {
        return dimension;
    }

    public Material getMaterial() {
        return material;
    }

    public Year getCollectionYear() {
        return collectionYear;
    }

    @Override
    public BigDecimal priceCorrection() {
        var r = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(dimension, 2, RoundingMode.HALF_EVEN));
        if (r.compareTo(BigDecimal.valueOf(0.5)) < 0)
            r = new BigDecimal("0.5");
        return r;
    }

    @Override
    public String show() {
        return "Handbag, " + super.show() + ", Dimension: " + dimension + "L, Material: " + material + "Collection year: " + collectionYear;
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

        if (!dimension.equals(handbag.dimension)) return false;
        if (!material.equals(handbag.material)) return false;
        return collectionYear.equals(handbag.collectionYear);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + dimension.hashCode();
        result = 31 * result + material.hashCode();
        result = 31 * result + collectionYear.hashCode();
        return result;
    }

    public enum Material {
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
