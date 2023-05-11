package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShippingCompany {
    private static long numberOfProducts = 0;

    private final String id;
    private final String name;
    private final Map<String, Order> orders;
    private final BigDecimal baseValueSmall;
    private final BigDecimal baseValueMedium;
    private final BigDecimal baseValueBig;
    private final BigDecimal fee;
    private BigDecimal revenue;
    private BigDecimal profitMargin;

    public ShippingCompany(String name, BigDecimal baseValueSmall, BigDecimal baseValueMedium, BigDecimal baseValueBig, BigDecimal fee, BigDecimal profitMargin) {
        this.id = nextAlphanumericId();
        this.name = name;
        this.baseValueSmall = baseValueSmall;
        this.baseValueMedium = baseValueMedium;
        this.baseValueBig = baseValueBig;
        this.fee = fee;
        this.revenue = BigDecimal.ZERO;
        this.orders = new HashMap<>();
        this.profitMargin = profitMargin;
    }

    protected ShippingCompany(ShippingCompany other) {
        this.id = other.id;
        this.name = other.name;
        this.baseValueSmall = other.baseValueSmall;
        this.baseValueMedium = other.baseValueMedium;
        this.baseValueBig = other.baseValueBig;
        this.fee = other.fee;
        this.revenue = other.revenue;
        this.orders = new HashMap<>(other.orders);
        this.profitMargin = other.profitMargin;
    }

    public String getId() {
        return this.id;
    }

    public BigDecimal getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(BigDecimal profitMargin) {
        this.profitMargin = profitMargin;
    }

    public void addOrder(Order order) {
        orders.put(order.getId(), order);
        revenue = revenue.add(shippingCost(order));
    }

    public BigDecimal getRevenue() {
        return revenue;
    }


    public BigDecimal shippingCost(long numberOfProducts) {
        return (numberOfProducts == 1 ? baseValueSmall : numberOfProducts < 5 ? baseValueMedium : baseValueBig).multiply(profitMargin)
                .multiply(BigDecimal.ONE.add(fee)).multiply(BigDecimal.valueOf(0.9));
    }

    public BigDecimal shippingCost(Order order) {
        return (order.getProducts()
                .size() == 1 ? baseValueSmall : numberOfProducts < 5 ? baseValueMedium : baseValueBig).multiply(profitMargin)
                .multiply(BigDecimal.ONE.add(fee)).multiply(BigDecimal.valueOf(0.9));
    }

    private String nextAlphanumericId() {
        return String.format("%4s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "ShippingCompany{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                ", baseValueSmall=" + baseValueSmall +
                ", baseValueMedium=" + baseValueMedium +
                ", baseValueBig=" + baseValueBig +
                ", fee=" + fee +
                ", revenue=" + revenue +
                ", profitMargin=" + profitMargin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippingCompany that = (ShippingCompany) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!orders.equals(that.orders)) return false;
        if (!baseValueSmall.equals(that.baseValueSmall)) return false;
        if (!baseValueMedium.equals(that.baseValueMedium)) return false;
        if (!baseValueBig.equals(that.baseValueBig)) return false;
        if (!fee.equals(that.fee)) return false;
        if (!revenue.equals(that.revenue)) return false;
        return profitMargin.equals(that.profitMargin);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + orders.hashCode();
        result = 31 * result + baseValueSmall.hashCode();
        result = 31 * result + baseValueMedium.hashCode();
        result = 31 * result + baseValueBig.hashCode();
        result = 31 * result + fee.hashCode();
        result = 31 * result + revenue.hashCode();
        result = 31 * result + profitMargin.hashCode();
        return result;
    }

    public ShippingCompany clone() {
        return new ShippingCompany(this);
    }
}
