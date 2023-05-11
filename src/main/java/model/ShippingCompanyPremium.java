package model;

import java.math.BigDecimal;

public class ShippingCompanyPremium extends ShippingCompany implements Premium {
    private BigDecimal premiumTax;

    public ShippingCompanyPremium(String name, BigDecimal baseValueSmall, BigDecimal baseValueMedium, BigDecimal baseValueBig, BigDecimal fee, BigDecimal profitMargin, BigDecimal premiumTax) {
        super(name, baseValueSmall, baseValueMedium, baseValueBig, fee, profitMargin);
        this.premiumTax = premiumTax;
    }

    public ShippingCompanyPremium(ShippingCompanyPremium other) {
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

        ShippingCompanyPremium that = (ShippingCompanyPremium) o;

        return premiumTax.equals(that.premiumTax);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + premiumTax.hashCode();
        return result;
    }

    @Override
    public ShippingCompanyPremium clone() {
        return new ShippingCompanyPremium(this);
    }
}
