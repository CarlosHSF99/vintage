package model;

import java.math.BigDecimal;

public class ShippingCompanyPremium extends ShippingCompany implements Premium {
    public ShippingCompanyPremium(String name, BigDecimal baseValueSmall, BigDecimal baseValueMedium, BigDecimal baseValueBig, BigDecimal fee) {
        super(name, baseValueSmall, baseValueMedium, baseValueBig, fee);
    }

    public BigDecimal shippingCost(Product product) {
        if (product instanceof Premium) {
            return null;
        }
        return super.shippingCost(0);
    }
}
