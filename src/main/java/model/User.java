package model;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class User {
    private static long numberOfUsers = 0;

    private final String code;
    private final String taxNumber;
    private final String name;
    private final Map<String, Product> productsBought;
    private final Map<String, Product> productsSelling;
    private final Map<String, Product> productsSold;
    private final List<Order> ordersMade;
    private final List<Order> ordersReceived;
    private final Map<String, Product> basket;
    private String email;
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
        this.ordersMade = new ArrayList<>();
        this.ordersReceived = new ArrayList<>();
        this.basket = new HashMap<>();
    }

    private User(User other) {
        this.code = other.code;
        this.taxNumber = other.taxNumber;
        this.name = other.name;
        this.email = other.email;
        this.address = other.address;
        this.productsBought = new HashMap<>(other.productsBought);
        this.productsSelling = new HashMap<>(other.productsSelling);
        this.productsSold = new HashMap<>(other.productsSold);
        this.ordersMade = new ArrayList<>(other.ordersMade);
        this.ordersReceived = new ArrayList<>(other.ordersReceived);
        this.basket = other.basket.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void addProductSold(Product product) {
        addProduct(productsSold, product);
    }

    public void addProductsSold(List<Product> products) {
        addProducts(productsSold, products);
    }

    public void addProductSelling(Product product) {
        addProduct(productsSelling, product);
    }

    public void addProductsSelling(List<Product> products) {
        addProducts(productsSelling, products);
    }

    public void addProductBought(Product product) {
        addProduct(productsBought, product);
    }

    public void addProductsBought(List<Product> products) {
        addProducts(productsBought, products);
    }

    public void addOrderMade(Order order) {
        ordersMade.add(order);
    }

    public void addOrdersMade(List<Order> orders) {
        ordersMade.addAll(orders);
    }

    public void addOrderReceived(Order order) {
        ordersReceived.add(order);
    }

    public void addOrdersReceived(List<Order> orders) {
        ordersReceived.addAll(orders);
    }

    public List<Product> getProductsBought() {
        return getProducts(productsBought);
    }

    public List<Product> getProductsSelling() {
        return getProducts(productsSelling);
    }

    public List<Product> getProductsSold() {
        return getProducts(productsSold);
    }

    public List<Order> getOrdersMade() {
        return ordersMade.stream().map(Order::clone).toList();
    }

    public List<Order> getOrdersReceived() {
        return ordersReceived.stream().map(Order::clone).toList();
    }

    private void addProduct(Map<String, Product> products, Product product) {
        products.put(product.getCode(), product);
    }

    private void addProducts(Map<String, Product> products, List<Product> newProducts) {
        products.putAll(newProducts.stream().collect(Collectors.toMap(Product::getCode, Function.identity())));
    }

    private List<Product> getProducts(Map<String, Product> products) {
        return products.values().stream().map(Product::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public BigDecimal revenue() {
        return ordersReceived.stream().map(Order::sellerRevenue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String nextAlphanumericCode() {
        return String.format("%6s", Long.toString(numberOfUsers++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "User{" +
                "code='" + code + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", name='" + name + '\'' +
                ", productsBought=" + productsBought +
                ", productsSelling=" + productsSelling +
                ", productsSold=" + productsSold +
                ", ordersMade=" + ordersMade +
                ", ordersReceived=" + ordersReceived +
                ", basket=" + basket +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!code.equals(user.code)) return false;
        if (!taxNumber.equals(user.taxNumber)) return false;
        if (!name.equals(user.name)) return false;
        if (!email.equals(user.email)) return false;
        if (!address.equals(user.address)) return false;
        if (!productsBought.equals(user.productsBought)) return false;
        if (!productsSelling.equals(user.productsSelling)) return false;
        if (!productsSold.equals(user.productsSold)) return false;
        if (!ordersMade.equals(user.ordersMade)) return false;
        if (!ordersReceived.equals(user.ordersReceived)) return false;
        return basket.equals(user.basket);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + taxNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + productsBought.hashCode();
        result = 31 * result + productsSelling.hashCode();
        result = 31 * result + productsSold.hashCode();
        result = 31 * result + ordersMade.hashCode();
        result = 31 * result + ordersReceived.hashCode();
        result = 31 * result + basket.hashCode();
        return result;
    }

    @Override
    public User clone() {
        return new User(this);
    }
}
