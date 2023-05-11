package model;

import java.math.BigDecimal;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class PremiumHandbag extends Handbag implements Premium {
    private Type type;

    public PremiumHandbag(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state, double dimension, String material, Year collectionYear, Type type) {
        super(sellerId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, dimension, material, collectionYear);
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
        return super.priceCorrection()
                .multiply(type.getAppreciationRate()
                        .pow((int) getCollectionYear().until(Year.now(), ChronoUnit.YEARS)));
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
        DESIGNER("1.2"), LUXURY_LEATHER("1.15"), VEGAN_LEATHER("1.1");

        private final BigDecimal appreciationRate;

        Type(String appreciationRate) {
            this.appreciationRate = new BigDecimal(appreciationRate);
        }

        public BigDecimal getAppreciationRate() {
            return appreciationRate;
        }
    }
}
