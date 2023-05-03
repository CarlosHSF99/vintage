package model;

import java.math.BigDecimal;

public class ShippingCompany {
    public ShippingCompany() {
    }

    public ShippingCompany(ShippingCompany other) {
    }

    public BigDecimal shippingCost(long count) {
        return null;
    }

    public ShippingCompany clone() {
        return new ShippingCompany(this);
    }
}
