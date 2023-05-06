package model;

import exceptions.ProductInCartUnavailable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Vintage {
    private final BigDecimal baseValueSmall;
    private final BigDecimal baseValueMedium;
    private final BigDecimal baseValueBig;
    private final BigDecimal orderFee;
    private final Map<String, Product> products;
    private final Map<String, User> users;
    private final Map<String, Order> orders;
    private final Map<String, ShippingCompany> shippingCompanies;

    public Vintage(String baseValueSmall, String baseValueMedium, String baseValueBig, String orderFee) {
        this.baseValueSmall = new BigDecimal(baseValueSmall);
        this.baseValueMedium = new BigDecimal(baseValueMedium);
        this.baseValueBig = new BigDecimal(baseValueBig);
        this.orderFee = new BigDecimal(orderFee);
        this.products = new HashMap<>();
        this.users = new HashMap<>();
        this.orders = new HashMap<>();
        this.shippingCompanies = new HashMap<>();
    }

    public void registerUser(String email, String name, String address, String taxNumber) {
        User user = new User(email, name, address, taxNumber);
        users.put(user.getId(), user);
    }

    public void registerShippingCompany(String name, boolean premium) {
        ShippingCompany newShippingCompany = premium ? new ShippingCompanyPremium(name, baseValueSmall, baseValueMedium, baseValueBig, orderFee) : new ShippingCompany(name, baseValueSmall, baseValueMedium, baseValueBig, orderFee);
        shippingCompanies.put(newShippingCompany.getId(), newShippingCompany);
    }

    public void publishProduct(String userCode, Product product) {
        Product productCopy = product.clone();
        products.put(product.getId(), productCopy);
        users.computeIfPresent(userCode, (k, v) -> {
            v.addProductSelling(productCopy);
            return v;
        });
    }

    public void addProductToUserCart(String productCode, String userCode) {
        if (products.containsKey(productCode) || users.containsKey(userCode)) {
            users.get(userCode).addProductToCart(products.get(productCode));
        }
    }

    public void orderUserCart(String buyerCode) throws ProductInCartUnavailable {
        // get buying user
        User buyer = users.get(buyerCode);

        // get cart from buying user
        List<Product> cart = buyer.returnCart();

        // check if cart is valid
        if (!products.values().containsAll(cart)) {
            cart.stream()
                    .filter(product -> products.containsKey(product.getId()))
                    .forEach(buyer::addProductToCart);
            throw new ProductInCartUnavailable("Product in cart unavailable.");
        }

        // remove products from list of selling products
        cart.stream().map(Product::getId).forEach(products.keySet()::remove);

        // auxiliary (seller code, shipping company) pair
        record SellerShippingCompanyPair(String sellerCode, ShippingCompany shippingCompany) {
        }

        // generate and distribute orders
        cart.stream()
                .map(Product::clone)
                .collect(Collectors.groupingBy(p -> new SellerShippingCompanyPair(p.getSellerCode(), p.getShippingCompany())))
                .entrySet()
                .stream()
                .map(e -> new Order(e.getValue(), e.getKey().sellerCode, buyerCode, e.getKey().shippingCompany))
                .forEach(order -> {
                    orders.put(order.getId(), order);
                    buyer.addOrderMade(order);
                    users.get(order.getSellerId()).addOrderReceived(order);
                    order.getShippingCompany().addOrder(order);
                });
    }

    public void returnOrder(String orderCode) {
        if (orders.containsKey(orderCode)) {
            orders.get(orderCode).setAsReturned();
        }
    }
}