package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShippingCompany {
    private final String name;
    private final List<Order> orders;
    private BigDecimal baseValueSmall;
    private BigDecimal baseValueMedium;
    private BigDecimal baseValueBig;
    private BigDecimal fee;
    private BigDecimal revenue;

    public ShippingCompany(String name, BigDecimal baseValueSmall, BigDecimal baseValueMedium, BigDecimal baseValueBig, BigDecimal fee) {
        this.name = name;
        this.baseValueSmall = baseValueSmall;
        this.baseValueMedium = baseValueMedium;
        this.baseValueBig = baseValueBig;
        this.fee = fee;
        this.revenue = BigDecimal.ZERO;
        this.orders = new ArrayList<>();
    }

    private ShippingCompany(ShippingCompany other) {
        this.name = other.name;
        this.baseValueSmall = other.baseValueSmall;
        this.baseValueMedium = other.baseValueMedium;
        this.baseValueBig = other.baseValueBig;
        this.fee = other.fee;
        this.revenue = other.revenue;
        this.orders = other.orders.stream().map(Order::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public void addOrder(Order order) {
        orders.add(order.clone());
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

    public ShippingCompany clone() {
        return new ShippingCompany(this);
    }
}
