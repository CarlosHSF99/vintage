package model;

import exceptions.ProductInCartUnavailable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Vintage {
    private final BigDecimal baseValueSmall;
    private final BigDecimal baseValueMedium;
    private final BigDecimal baseValueBig;
    private final BigDecimal orderFee;
    private final Map<String, User> users;
    private final List<ShippingCompany> shippingCompanies;
    private final Map<String, Product> productsSelling;
    private final Map<String, Product> productsSold;
    private final List<Order> orders;

    public Vintage(String baseValueSmall, String baseValueMedium, String baseValueBig, String orderFee, List<Order> orders) {
        this.baseValueSmall = new BigDecimal(baseValueSmall);
        this.baseValueMedium = new BigDecimal(baseValueMedium);
        this.baseValueBig = new BigDecimal(baseValueBig);
        this.orderFee = new BigDecimal(orderFee);
        this.users = new HashMap<>();
        this.shippingCompanies = new ArrayList<>();
        this.productsSelling = new HashMap<>();
        this.productsSold = new HashMap<>();
        this.orders = orders;
    }

    public void registerUser(String email, String name, String address, String taxNumber) {
        User user = new User(email, name, address, taxNumber);
        users.put(user.getCode(), user);
    }

    public void registerShippingCompany(String name, boolean premium) {
        shippingCompanies.add(premium ? new ShippingCompanyPremium(name, baseValueSmall, baseValueMedium, baseValueBig, orderFee) : new ShippingCompany(name, baseValueSmall, baseValueMedium, baseValueBig, orderFee));
    }

    public void publishProduct(String userCode, Product product) {
        Product productCopy = product.clone();
        productsSelling.put(product.getCode(), productCopy);
        users.computeIfPresent(userCode, (k, v) -> {
            v.addProductSelling(productCopy);
            return v;
        });
    }

    public void addProductToUserCart(String productCode, String userCode) {
        if (productsSelling.containsKey(productCode) || users.containsKey(userCode)) {
            users.get(userCode).addProductToCart(productsSelling.get(productCode));
        }
    }

    public void orderUserCart(String userCode) throws ProductInCartUnavailable {
        record SellerShippingCompanyPair(String sellerCode, ShippingCompany shippingCompany) {
        }
        User client = users.get(userCode);
        List<Product> cart = client.returnCart();
        if (!productsSelling.values().containsAll(cart)) {
            for (var product : cart.stream().filter(product -> productsSelling.containsKey(product.getCode())).toList()) {
                client.addProductToCart(product);
            }
            throw new ProductInCartUnavailable("Product in cart unavailable.");
        }
        cart.forEach(product -> {
            productsSelling.remove(product.getCode());
            productsSold.put(product.getCode(), product);
            client.addProductBought(product);
            User seller = users.get(product.getSellerCode());
            seller.addProductSold(product);
            seller.removeProductSelling(product.getCode());
        });
        cart.stream()
                .map(Product::clone)
                .collect(Collectors.groupingBy(p -> new SellerShippingCompanyPair(p.getSellerCode(), p.getShippingCompany())))
                .entrySet()
                .stream()
                .map(e -> new Order(e.getValue(), e.getKey().sellerCode, e.getKey().shippingCompany))
                .forEach(order -> {
                    orders.add(order);
                    client.addOrderMade(order);
                    users.get(order.getSellerCode()).addOrderReceived(order);
                    order.getShippingCompany().addOrder(order);
                });
        /*
        orders.addAll(newOrders);
        users.get(userCode).addOrdersMade(orders);
        for (var order : orders) {
            users.get(order.getSellerCode()).addOrderReceived(order);
        }
         */
    }
}
