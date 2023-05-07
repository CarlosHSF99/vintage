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
    private final String buyerId;
    private final String sellerId;
    private final String shippingCompanyId;
    private final Map<String, Product> products;
    private final LocalDateTime creationDate;
    private final BigDecimal productsCost;
    private final BigDecimal shippingCost;
    private final BigDecimal vintageFees;
    private final BigDecimal totalCost;
    private LocalDateTime deliveryDateTime;
    private Status status;

    public Order(Collection<Product> products, String buyerId, String sellerId, String shippingCompanyId, BigDecimal shippingCost) {
        this.id = nextAlphanumericId();
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.shippingCompanyId = shippingCompanyId;
        this.products = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        this.creationDate = LocalDateTime.now();
        this.productsCost = products.stream().map(Product::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.shippingCost = shippingCost;
        this.vintageFees = products.stream()
                .map(product -> product.isNew() ? NEW_FEE : USED_FEE)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalCost = this.productsCost.add(this.shippingCost).add(vintageFees);
        this.status = Status.INITIALIZED;
    }

    private Order(Order other) {
        this.id = nextAlphanumericId();
        this.buyerId = other.buyerId;
        this.sellerId = other.sellerId;
        this.shippingCompanyId = other.shippingCompanyId;
        this.products = other.products.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.creationDate = other.creationDate;
        this.deliveryDateTime = other.deliveryDateTime;
        this.productsCost = other.productsCost;
        this.shippingCost = other.shippingCost;
        this.vintageFees = other.vintageFees;
        this.totalCost = other.totalCost;
        this.status = other.status;
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

    public String getShippingCompanyId() {
        return shippingCompanyId;
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

    public BigDecimal totalCost() {
        return totalCost;
    }

    public BigDecimal productsCost() {
        return productsCost;
    }

    public BigDecimal shippingCost() {
        return shippingCost;
    }

    public BigDecimal vintageFees() {
        return vintageFees;
    }

    public BigDecimal sellerRevenue() {
        return status != Status.RETURNED ? productsCost : BigDecimal.ZERO;
    }

    private String nextAlphanumericId() {
        return String.format("%8s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", shippingCompanyId='" + shippingCompanyId + '\'' +
                ", products=" + products +
                ", creationDate=" + creationDate +
                ", productsCost=" + productsCost +
                ", shippingCost=" + shippingCost +
                ", vintageFees=" + vintageFees +
                ", totalCost=" + totalCost +
                ", deliveryDateTime=" + deliveryDateTime +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;
        if (!sellerId.equals(order.sellerId)) return false;
        if (!buyerId.equals(order.buyerId)) return false;
        if (!shippingCompanyId.equals(order.shippingCompanyId)) return false;
        if (!products.equals(order.products)) return false;
        if (!creationDate.equals(order.creationDate)) return false;
        if (!productsCost.equals(order.productsCost)) return false;
        if (!shippingCost.equals(order.shippingCost)) return false;
        if (!vintageFees.equals(order.vintageFees)) return false;
        if (!totalCost.equals(order.totalCost)) return false;
        if (!Objects.equals(deliveryDateTime, order.deliveryDateTime))
            return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sellerId.hashCode();
        result = 31 * result + buyerId.hashCode();
        result = 31 * result + shippingCompanyId.hashCode();
        result = 31 * result + products.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + productsCost.hashCode();
        result = 31 * result + shippingCost.hashCode();
        result = 31 * result + vintageFees.hashCode();
        result = 31 * result + totalCost.hashCode();
        result = 31 * result + (deliveryDateTime != null ? deliveryDateTime.hashCode() : 0);
        result = 31 * result + status.hashCode();
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
