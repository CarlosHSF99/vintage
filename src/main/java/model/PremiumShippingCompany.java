package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PremiumShippingCompany extends ShippingCompany implements Premium, Serializable {
    private BigDecimal premiumTax;

    public PremiumShippingCompany(String name, BigDecimal baseValueSmall, BigDecimal baseValueMedium, BigDecimal baseValueBig, BigDecimal fee, BigDecimal profitMargin, BigDecimal premiumTax) {
        super(name, baseValueSmall, baseValueMedium, baseValueBig, fee, profitMargin);
        this.premiumTax = premiumTax;
    }

    public PremiumShippingCompany(PremiumShippingCompany other) {
        super(other);
        this.premiumTax = other.premiumTax;
    }

    public BigDecimal getPremiumTax() {
        return premiumTax;
    }

    public void setPremiumTax(BigDecimal premiumTax) {
        this.premiumTax = premiumTax;
    }

    @Override
    public BigDecimal shippingCost(Order order) {
        return super.shippingCost(order)
                .add(premiumTax.multiply(BigDecimal.valueOf(order.getProducts()
                        .stream()
                        .filter(product -> product instanceof Premium)
                        .count())));
    }

    @Override
    public String toString() {
        return "ShippingCompanyPremium{" +
                "premiumTax=" + premiumTax +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PremiumShippingCompany that = (PremiumShippingCompany) o;

        return premiumTax.equals(that.premiumTax);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + premiumTax.hashCode();
        return result;
    }

    @Override
    public PremiumShippingCompany clone() {
        return new PremiumShippingCompany(this);
    }
}
