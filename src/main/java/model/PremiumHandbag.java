package model;

import java.math.BigDecimal;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class PremiumHandbag extends Handbag implements Premium {
    private Type type;

    public PremiumHandbag(String seller, String description, String brand, String basePrice, int numberOfPreviousOwners, ShippingCompany shippingCompany, double dimension, String material, Year collectionYear, Type type) {
        super(seller, description, brand, basePrice, numberOfPreviousOwners, shippingCompany, dimension, material, collectionYear);
        this.type = type;
    }

    public PremiumHandbag(PremiumHandbag other) {
        super(other);
        this.type = other.type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public BigDecimal priceCorrection() {
        BigDecimal appreciation = type.getAppreciationRate();
        long year = getCollectionYear().until(Year.now(), ChronoUnit.YEARS);
        for (int i = 0; i < year; i++) {
            appreciation = appreciation.add(appreciation.multiply(type.getAppreciationRate()));
        }
        return super.priceCorrection().subtract(appreciation);
    }

    @Override
    public BigDecimal price() {
        return getBasePrice().multiply(BigDecimal.ONE.subtract(priceCorrection()));
    }

    @Override
    public String toString() {
        return "PremiumHandbag{" + super.toString() + ", type=" + type + "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PremiumHandbag that = (PremiumHandbag) o;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    enum Type {
        DESIGNER("0.2"), LUXURY_LEATHER("0.15"), VEGAN_LEATHER("0.1");

        private final BigDecimal appreciationRate;

        Type(String appreciationRate) {
            this.appreciationRate = new BigDecimal(appreciationRate);
        }

        public BigDecimal getAppreciationRate() {
            return appreciationRate;
        }
    }
}
