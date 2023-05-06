package model;

import exceptions.LateReturnException;
import exceptions.StatusOrderException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Order {
    private static long numberOfProducts = 0;

    private final String code;
    private static final BigDecimal NEW_FEE = new BigDecimal("0.5");
    private static final BigDecimal USED_FEE = new BigDecimal("0.25");
    private final Map<String, Product> products;
    private final LocalDateTime creationDate;
    private LocalDateTime deliveryDateTime;
    private Status status;
    private final String sellerCode;
    private final ShippingCompany shippingCompany;

    public Order(Collection<Product> products, String sellerCode, ShippingCompany shippingCompany) {
        this.code = nextAlphanumericCode();
        this.products = products.stream().collect(Collectors.toMap(Product::getCode, Function.identity()));
        this.sellerCode = sellerCode;
        this.shippingCompany = shippingCompany.clone();
        this.creationDate = LocalDateTime.now();
        this.status = Status.INITIALIZED;
    }

    private Order(Order other) {
        this.code = nextAlphanumericCode();
        this.products = other.products.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.creationDate = other.creationDate;
        this.deliveryDateTime = other.deliveryDateTime;
        this.status = other.status;
        this.sellerCode = other.sellerCode;
        this.shippingCompany = other.shippingCompany.clone();
    }

    public String getCode() {
        return code;
    }

    public Map<String, Product> getProducts() {
        return new HashMap<>(products);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public Status getStatus() {
        return status;
    }

    public ShippingCompany getShippingCompany() {
        return shippingCompany;
    }

    public void expedite() throws StatusOrderException {
        if (status != Status.INITIALIZED) {
            throw new StatusOrderException("Trying to expedite " + status.name() + "order.\nOnly initialized orders can be expedited.");
        }
        status = Status.EXPEDITED;
    }

    public void deliver() throws StatusOrderException {
        if (status != Status.EXPEDITED) {
            throw new StatusOrderException("Trying to deliver " + status.name() + "order.\nOnly expedited orders can be delivered.");
        }
        deliveryDateTime = LocalDateTime.now();
        status = Status.DELIVERED;
    }

    public void refund() throws StatusOrderException, LateReturnException {
        if (status != Status.DELIVERED) {
            throw new StatusOrderException("Trying to return " + status.name() + "order.\nOnly delivered orders can be returned.");
        }
        if (deliveryDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS) > 48) {
            throw new LateReturnException("Trying to return order past return deadline.");
        }
        status = Status.RETURNED;
    }

    public BigDecimal finalCost() {
        return products.values()
                .stream()
                .map(product -> product.price().add(product.isUsed() ? USED_FEE : NEW_FEE))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(shippingCost());
    }

    public BigDecimal shippingCost() {
        return shippingCompany.shippingCost(products.size());
    }

    public BigDecimal sellerRevenue() {
        return status != Status.RETURNED ? products.values()
                .stream()
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
    }

    private String nextAlphanumericCode() {
        return String.format("%8s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", creationDate=" + creationDate +
                ", deliveryDateTime=" + deliveryDateTime +
                ", status=" + status +
                ", sellerCode='" + sellerCode + '\'' +
                ", shippingCompany=" + shippingCompany +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!products.equals(order.products)) return false;
        if (!creationDate.equals(order.creationDate)) return false;
        if (!Objects.equals(deliveryDateTime, order.deliveryDateTime))
            return false;
        if (status != order.status) return false;
        if (!sellerCode.equals(order.sellerCode)) return false;
        return shippingCompany.equals(order.shippingCompany);
    }

    @Override
    public int hashCode() {
        int result = products.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (deliveryDateTime != null ? deliveryDateTime.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + sellerCode.hashCode();
        result = 31 * result + shippingCompany.hashCode();
        return result;
    }

    @Override
    public Order clone() {
        return new Order(this);
    }

    public enum Status {
        INITIALIZED, EXPEDITED, DELIVERED, RETURNED
    }
}
