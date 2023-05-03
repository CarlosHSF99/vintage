package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShippingCompany {
    private BigDecimal baseValue;
    private BigDecimal fee;
    private BigDecimal revenue;
    private final List<Order> orders;

    public ShippingCompany(BigDecimal baseValue, BigDecimal fee) {
        this.baseValue = baseValue;
        this.fee = fee;
        this.revenue = BigDecimal.ZERO;
        this.orders = new ArrayList<>();
    }

    private ShippingCompany(ShippingCompany other) {
        this.baseValue = other.baseValue;
        this.fee = other.fee;
        this.revenue = other.revenue;
        this.orders = other.orders.stream().map(Order::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public void addOrder(Order order) {
        orders.add(order.clone());
        revenue = revenue.add(shippingCost(order.getProducts().size()));
    }

    public BigDecimal shippingCost(long count) {
        return baseValue.multiply(BigDecimal.ONE.add(fee));
    }

    public ShippingCompany clone() {
        return new ShippingCompany(this);
    }
}
