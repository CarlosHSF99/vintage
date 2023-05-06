package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShippingCompany {
    private static long numberOfProducts = 0;

    private final String id;
    private final String name;
    private final Map<String, Order> orders;
    private BigDecimal baseValueSmall;
    private BigDecimal baseValueMedium;
    private BigDecimal baseValueBig;
    private BigDecimal fee;
    private BigDecimal revenue;

    public ShippingCompany(String name, BigDecimal baseValueSmall, BigDecimal baseValueMedium, BigDecimal baseValueBig, BigDecimal fee) {
        this.id = nextAlphanumericId();
        this.name = name;
        this.baseValueSmall = baseValueSmall;
        this.baseValueMedium = baseValueMedium;
        this.baseValueBig = baseValueBig;
        this.fee = fee;
        this.revenue = BigDecimal.ZERO;
        this.orders = new HashMap<>();
    }

    private ShippingCompany(ShippingCompany other) {
        this.id = other.id;
        this.name = other.name;
        this.baseValueSmall = other.baseValueSmall;
        this.baseValueMedium = other.baseValueMedium;
        this.baseValueBig = other.baseValueBig;
        this.fee = other.fee;
        this.revenue = other.revenue;
        this.orders = new HashMap<>(other.orders);
    }

    public String getId() {
        return this.id;
    }

    public void addOrder(Order order) {
        orders.put(order.getId(), order);
        revenue = revenue.add(shippingCost(order.getProducts().size()));
    }

    public BigDecimal shippingCost(long numberOfProducts) {
        if (numberOfProducts == 1) {
            return baseValueSmall.multiply(BigDecimal.ONE.add(fee));
        } else if (numberOfProducts >= 2 && numberOfProducts <= 5) {
            return baseValueMedium.multiply(BigDecimal.ONE.add(fee));
        } else {
            return baseValueBig.multiply(BigDecimal.ONE.add(fee));
        }
    }

    private String nextAlphanumericId() {
        return String.format("%4s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    public ShippingCompany clone() {
        return new ShippingCompany(this);
    }
}
