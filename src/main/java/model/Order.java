package model;

import exceptions.LateReturnException;
import exceptions.StatusOrderException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Order {
    private static final BigDecimal NEW_FEE = new BigDecimal("0.5");
    private static final BigDecimal USED_FEE = new BigDecimal("0.25");
    private static long numberOfProducts = 0;
    private final String id;
    private final Map<String, Product> products;
    private final LocalDateTime creationDate;
    private final String sellerId;
    private final String buyerId;
    private final ShippingCompany shippingCompany;
    private LocalDateTime deliveryDateTime;
    private Status status;

    public Order(Collection<Product> products, String sellerId, String buyerId, ShippingCompany shippingCompany) {
        this.id = nextAlphanumericId();
        this.products = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.shippingCompany = shippingCompany.clone();
        this.creationDate = LocalDateTime.now();
        this.status = Status.INITIALIZED;
    }

    private Order(Order other) {
        this.id = nextAlphanumericId();
        this.products = other.products.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.creationDate = other.creationDate;
        this.deliveryDateTime = other.deliveryDateTime;
        this.status = other.status;
        this.sellerId = other.sellerId;
        this.shippingCompany = other.shippingCompany.clone();
        this.buyerId = other.buyerId;
    }

    public String getId() {
        return id;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public Status getStatus() {
        return status;
    }

    public boolean wasReturned() {
        return status == Status.RETURNED;
    }

    public boolean wasNotReturned() {
        return status != Status.RETURNED;
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

    public void setAsReturned() throws StatusOrderException, LateReturnException {
        if (status != Status.DELIVERED) {
            throw new StatusOrderException("Trying to return " + status.name() + "order.\nOnly delivered orders can be returned.");
        }
        if (deliveryDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS) > 48) {
            throw new LateReturnException("Trying to return order past return deadline.");
        }
        status = Status.RETURNED;
    }

    public BigDecimal finalCost() {
        return productsCost().add(shippingCost()).add(vintageFees());
    }

    public BigDecimal productsCost() {
        return products.values().stream().map(Product::price).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal shippingCost() {
        return shippingCompany.shippingCost(products.size());
    }

    public BigDecimal vintageFees() {
        return products.values()
                .stream()
                .map(product -> product.isNew() ? NEW_FEE : USED_FEE)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sellerRevenue() {
        return status != Status.RETURNED ? products.values()
                .stream()
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
    }

    private String nextAlphanumericId() {
        return String.format("%8s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", creationDate=" + creationDate +
                ", deliveryDateTime=" + deliveryDateTime +
                ", status=" + status +
                ", sellerCode='" + sellerId + '\'' +
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
        if (!sellerId.equals(order.sellerId)) return false;
        return shippingCompany.equals(order.shippingCompany);
    }

    @Override
    public int hashCode() {
        int result = products.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (deliveryDateTime != null ? deliveryDateTime.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + sellerId.hashCode();
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
