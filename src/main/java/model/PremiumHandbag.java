package model;

import util.TimeSimulation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class PremiumHandbag extends Handbag implements Premium, Serializable {
    private final BigDecimal appreciationRate;

    public PremiumHandbag(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state, BigDecimal dimension, Material material, Year collectionYear, BigDecimal appreciationRate) {
        super(sellerId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, dimension, material, collectionYear);
        this.appreciationRate = appreciationRate;
    }

    public BigDecimal getAppreciationRate() {
        return appreciationRate;
    }

    @Override
    public BigDecimal priceCorrection() {
        return super.priceCorrection()
                .multiply(appreciationRate.pow((int) getCollectionYear().until(Year.now(TimeSimulation.getClock()), ChronoUnit.YEARS)));
    }

    @Override
    public String toString() {
        return "PremiumHandbag{" + super.toString() + ", type=" + appreciationRate + "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PremiumHandbag that = (PremiumHandbag) o;

        return appreciationRate.compareTo(that.appreciationRate) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + appreciationRate.hashCode();
        return result;
    }
}
