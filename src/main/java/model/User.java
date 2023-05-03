package model;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class User {
    private static long numberOfUsers = 0;

    private final String code;
    private final String taxNumber;
    private final Map<String, Product> productsBought;
    private final Map<String, Product> productsSelling;
    private final Map<String, Product> productsSold;
    private final Map<String, Order> ordersMade;
    private final Map<String, Order> ordersReceived;
    private final Map<String, Product> basket;
    private String email;
    private String name;
    private String address;

    public User(String email, String name, String address, String taxNumber) {
        this.code = nextAlphanumericCode();
        this.email = email;
        this.name = name;
        this.address = address;
        this.taxNumber = taxNumber;
        this.productsBought = new HashMap<>();
        this.productsSelling = new HashMap<>();
        this.productsSold = new HashMap<>();
        this.ordersMade = new HashMap<>();
        this.ordersReceived = new HashMap<>();
        this.basket = new HashMap<>();
    }

    public void addProductToBasket(Product product) {
        basket.put(product.getCode(), product.clone());
    }

    // add exception
    public void removeProductFromBasket(String productCode) {
        basket.remove(productCode);
    }

    // add exception
    public void removeProductFromBasket(Product product) {
        basket.remove(product.getCode());
    }

    public void clearBasket() {
        basket.clear();
    }

    public List<Product> returnBasket() {
        var products = basket.values().stream().map(Product::clone).toList();
        basket.clear();
        return products;
    }

    public List<Product> getBasket() {
        return basket.values().stream().map(Product::clone).toList();
    }

    public String getCode() {
        return code;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addSoldProduct(Product product) {
        addProduct(productsSold, product);
    }

    public void addSellingProduct(Product product) {
        addProduct(productsSelling, product);
    }

    public void addBoughtProduct(Product product) {
        addProduct(productsBought, product);
    }

    public void addOrderMade(Order order) {
        addOrder(ordersMade, order);
    }

    public void addOrderReceived(Order order) {
        addOrder(ordersReceived, order);
    }

    public Collection<Product> productsBought() {
        return getProducts(productsBought);
    }

    public Collection<Product> productsSelling() {
        return getProducts(productsSelling);
    }

    public Collection<Product> productsSold() {
        return getProducts(productsSold);
    }

    public Collection<Order> ordersMade() {
        return getOrders(ordersMade);
    }

    public Collection<Order> ordersReceived() {
        return getOrders(ordersReceived);
    }

    private void addProduct(Map<String, Product> products, Product product) {
        products.put(product.getCode(), product);
    }

    private void addProducts(Map<String, Product> products, Collection<Product> newProducts) {
        products.putAll(newProducts.stream().collect(Collectors.toMap(Product::getCode, Function.identity())));
    }

    /**
     * FIX ME
     */
    private void addOrder(Map<String, Order> orders, Order order) {
        orders.put(order.toString()/*.getCode()*/, order);
    }

    /**
     * FIX ME
     */
    private void addOrders(Map<String, Order> orders, Collection<Order> newOrders) {
        orders.putAll(newOrders.stream().collect(Collectors.toMap(Order::toString/*getCode*/, Function.identity())));
    }

    private Collection<Product> getProducts(Map<String, Product> products) {
        return products.values().stream().map(Product::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    private Collection<Order> getOrders(Map<String, Order> orders) {
        return orders.values().stream().map(Order::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public BigDecimal revenue() {
        return ordersReceived.values().stream().map(Order::sellerRevenue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String nextAlphanumericCode() {
        return String.format("%6s", Long.toString(numberOfUsers++, 36)).replace(' ', '0');
    }
}
