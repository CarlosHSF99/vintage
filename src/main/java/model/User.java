package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class User {
    private static long numberOfUsers = 0;

    private final String id;
    private final String taxNumber;
    private final String name;
    private final Map<String, Product> products;
    private final Map<String, Order> ordersMade;
    private final Map<String, Order> ordersReceived;
    private final Map<String, Product> cart;
    private String email;
    private String address;
    private BigDecimal revenue;
    private BigDecimal spending;

    public User(String email, String name, String address, String taxNumber) {
        this.id = nextAlphanumericId();
        this.email = email;
        this.name = name;
        this.address = address;
        this.taxNumber = taxNumber;
        this.products = new HashMap<>();
        this.ordersMade = new HashMap<>();
        this.ordersReceived = new HashMap<>();
        this.cart = new HashMap<>();
        this.revenue = BigDecimal.ZERO;
        this.spending = BigDecimal.ZERO;
    }

    private User(User other) {
        this.id = other.id;
        this.taxNumber = other.taxNumber;
        this.name = other.name;
        this.email = other.email;
        this.address = other.address;
        this.products = new HashMap<>(other.products);
        this.ordersMade = new HashMap<>(other.ordersMade);
        this.ordersReceived = new HashMap<>(other.ordersReceived);
        this.cart = other.cart.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.revenue = other.revenue;
        this.spending = other.spending;
    }

    public String getId() {
        return id;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addProductToCart(Product product) {
        cart.put(product.getId(), product.clone());
    }

    // add exception
    public void removeProductFromCart(String productCode) {
        cart.remove(productCode);
    }

    // add exception
    public void removeProductFromCart(Product product) {
        cart.remove(product.getId());
    }

    public void clearCart() {
        cart.clear();
    }

    public List<Product> getCart() {
        return cart.values().stream().map(Product::clone).toList();
    }

    public List<Product> returnCart() {
        var products = cart.values().stream().map(Product::clone).toList();
        cart.clear();
        return products;
    }

    public void addProductSelling(Product product) {
        addProduct(products, product);
    }

    public void addProductsSelling(List<Product> products) {
        addProducts(this.products, products);
    }

    public void addOrderMade(Order order) {
        ordersMade.put(order.getId(), order);
        spending = spending.add(order.productsCost());
    }

    public void addOrdersMade(List<Order> orders) {
        orders.forEach(this::addOrderMade);
    }

    public void addOrderReceived(Order order) {
        ordersReceived.put(order.getId(), order);
        order.getProducts().stream().map(Product::getId).forEach(products.keySet()::remove);
        revenue = revenue.add(order.productsCost());
    }

    public void addOrdersReceived(List<Order> orders) {
        orders.forEach(this::addOrderReceived);
    }

    public List<Product> getProducts() {
        return products.values().stream().toList();
    }

    public List<Product> getProductsSold() {
        return ordersReceived.values().stream().flatMap(order -> order.getProducts().stream()).toList();
    }

    public List<Product> getProductsBought() {
        return ordersMade.values().stream().flatMap(order -> order.getProducts().stream()).toList();
    }

    public List<Order> getOrdersMade() {
        return ordersMade.values().stream().map(Order::clone).toList();
    }

    public List<Order> getOrdersReceived() {
        return ordersReceived.values().stream().map(Order::clone).toList();
    }

    public void removeProductSelling(String productCode) {
        products.remove(productCode);
    }

    private void addProduct(Map<String, Product> products, Product product) {
        products.put(product.getId(), product);
    }

    private void addProducts(Map<String, Product> products, List<Product> newProducts) {
        products.putAll(newProducts.stream().collect(Collectors.toMap(Product::getId, Function.identity())));
    }

    private List<Product> getProducts(Map<String, Product> products) {
        return products.values().stream().map(Product::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public BigDecimal getRevenue() {
        return revenue;
        // return ordersReceived.values().stream().map(Order::sellerRevenue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getRevenue(LocalDateTime from, LocalDateTime to) {
        return ordersReceived.values()
                .stream()
                .filter(order -> order.getCreationDate().isAfter(from) && order.getCreationDate().isBefore(to))
                .map(Order::sellerRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSpending() {
        return spending;
        // return ordersReceived.values().stream().map(Order::sellerRevenue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSpending(LocalDateTime from, LocalDateTime to) {
        return ordersMade.values()
                .stream()
                .filter(order -> order.getCreationDate().isAfter(from) && order.getCreationDate().isBefore(to))
                .map(Order::sellerRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String nextAlphanumericId() {
        return String.format("%6s", Long.toString(numberOfUsers++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", ordersMade=" + ordersMade +
                ", ordersReceived=" + ordersReceived +
                ", cart=" + cart +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", revenue=" + revenue +
                ", spending=" + spending +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + taxNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + products.hashCode();
        result = 31 * result + ordersMade.hashCode();
        result = 31 * result + ordersReceived.hashCode();
        result = 31 * result + cart.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + revenue.hashCode();
        result = 31 * result + spending.hashCode();
        return result;
    }

    @Override
    public User clone() {
        return new User(this);
    }
}
