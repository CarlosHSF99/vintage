package model;

import exceptions.EmptyOrderException;
import exceptions.LateReturnException;
import exceptions.StatusOrderException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Order {
    private static final BigDecimal SERVICE_FEE_NEW = new BigDecimal("0.5");
    private static final BigDecimal SERVICE_FEE_USED = new BigDecimal("0.25");
    private final Map<String, Product> products;
    private LocalDateTime creationDate;
    private Status status;

    public Order(Product product) {
        products = new HashMap<>();
        products.put(product.getCode(), product);
        status = Status.PENDING;
    }

    private Order(Collection<Product> products, Status status) {
        this.products = new HashMap<>();
        products.forEach(p -> this.products.put(p.getCode(), p));
        this.status = status;
    }

    private Order(Order other) {
        products = new HashMap<>(other.products); // .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        creationDate = other.creationDate;
        status = other.status;
    }

    public Map<String, Product> getProducts() {
        return new HashMap<>(products);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public Order OrderForSeller(String sellerCode) {
        return new Order(products.values().stream().filter(p -> p.getSeller().equals(sellerCode)).toList(), status);
    }

    public Order OrderForShippingCompany(String shippingCompany) {
        return new Order(products.values()
                .stream()
                .filter(p -> p.getSeller().equals(shippingCompany))
                .toList(), status);
    }

    public void addProduct(Product product) {
        products.put(product.getCode(), product);
    }

    public void finish() {
        if (status != Status.PENDING) {
            throw new StatusOrderException("Trying to finnish " + status.name() + "order.\nOnly pending orders can be finished.");
        }
        status = Status.FINALIZED;
        creationDate = LocalDateTime.now();
    }

    public void expedite() throws StatusOrderException {
        if (status != Status.FINALIZED) {
            throw new StatusOrderException("Trying to expedite " + status.name() + "order.\nOnly finnish orders can be expedited.");
        }
        status = Status.EXPEDITED;
    }

    public void deliver() {
        if (status != Status.EXPEDITED) {
            throw new StatusOrderException("Trying to deliver " + status.name() + "order.\nOnly expedited orders can be delivered.");
        }
        status = Status.DELIVERED;
    }

    public void refund() throws LateReturnException {
        if (status != Status.DELIVERED) {
            throw new StatusOrderException("Trying to return " + status.name() + "order.\nOnly delivered orders can be returned.");
        }
        status = Status.RETURNED;
    }

    public void removeProduct(String productCode) throws EmptyOrderException {
        products.remove(productCode);
        if (products.isEmpty()) {
            throw new EmptyOrderException("Order is empty.");
        }
    }

    public BigDecimal finalPrice() {
        return products.values()
                .stream()
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(totalShippingCost());
    }

    public BigDecimal totalShippingCost() {
        return products.values()
                .stream()
                .collect(Collectors.groupingBy(Product::getSeller))
                .values()
                .stream()
                .map(v -> v.stream().collect(Collectors.groupingBy(Product::getShippingCompany)).entrySet())
                .flatMap(Set::stream)
                .map(entry -> entry.getKey().shippingCost(entry.getValue().size()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sellerRevenue() {
        return products.values()
                .stream()
                .map(product -> product.price()
                        .add(product.shippingPrice())
                        .subtract(product.isUsed() ? SERVICE_FEE_USED : SERVICE_FEE_NEW))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order clone() {
        return new Order(this);
    }

    public enum Status {
        PENDING, FINALIZED, EXPEDITED, DELIVERED, RETURNED
    }
}
