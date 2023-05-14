package model;

import exceptions.ProductInCartUnavailable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Vintage implements Serializable {
    private final BigDecimal baseValueSmall;
    private final BigDecimal baseValueMedium;
    private final BigDecimal baseValueBig;
    private final BigDecimal orderFee;
    private final Map<String, Product> products;
    private final Map<String, User> users;
    private final Map<String, Order> orders;
    private final Map<String, ShippingCompany> shippingCompanies;
    private BigDecimal revenue;

    public Vintage(BigDecimal baseValueSmall, BigDecimal baseValueMedium, BigDecimal baseValueBig, BigDecimal orderFee) {
        this.baseValueSmall = baseValueSmall;
        this.baseValueMedium = baseValueMedium;
        this.baseValueBig = baseValueBig;
        this.orderFee = orderFee;
        this.products = new HashMap<>();
        this.users = new HashMap<>();
        this.orders = new LinkedHashMap<>();
        this.shippingCompanies = new HashMap<>();
        this.revenue = BigDecimal.ZERO;
    }

    public Vintage(String baseValueSmall, String baseValueMedium, String baseValueBig, String orderFee) {
        this.baseValueSmall = new BigDecimal(baseValueSmall);
        this.baseValueMedium = new BigDecimal(baseValueMedium);
        this.baseValueBig = new BigDecimal(baseValueBig);
        this.orderFee = new BigDecimal(orderFee);
        this.products = new HashMap<>();
        this.users = new HashMap<>();
        this.orders = new LinkedHashMap<>();
        this.shippingCompanies = new HashMap<>();
        this.revenue = BigDecimal.ZERO;
    }

    public void registerUser(String email, String name, String address, String taxNumber) {
        User user = new User(email, name, address, taxNumber);
        users.put(user.getId(), user);
    }

    public void registerShippingCompany(String name, BigDecimal profitMargin) {
        ShippingCompany newShippingCompany = new ShippingCompany(name, baseValueSmall, baseValueMedium, baseValueBig, orderFee, profitMargin);
        shippingCompanies.put(newShippingCompany.getId(), newShippingCompany);
    }

    public void registerPremiumShippingCompany(String name, BigDecimal profitMargin, BigDecimal premiumTax) {
        ShippingCompany newShippingCompany = new PremiumShippingCompany(name, baseValueSmall, baseValueMedium, baseValueBig, orderFee, profitMargin, premiumTax);
        shippingCompanies.put(newShippingCompany.getId(), newShippingCompany);
    }

    public void publishProduct(String userId, Product product) {
        products.put(product.getId(), product);
        users.computeIfPresent(userId, (k, v) -> {
            v.addProductSelling(product);
            return v;
        });
    }

    public void addProductToUserCart(String userId, String productId) {
        if (users.containsKey(userId) && products.containsKey(productId)) {
            users.get(userId).addProductToCart(products.get(productId));
        }
    }

    public void removeProductFromUserCart(String userId, String productId) {
        if (users.containsKey(userId) && products.containsKey(productId)) {
            users.get(userId).removeProductFromCart(products.get(productId));
        }
    }

    public List<Product> getProducts() {
        return products.values().stream().toList();
    }

    public List<ShippingCompany> getShippingCompanies() {
        return shippingCompanies.values().stream().map(ShippingCompany::clone).toList();
    }

    public Optional<String> getUserIdByEmail(String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().map(User::getId);
    }

    public Optional<User> getUser(String userId) {
        return Optional.ofNullable(users.get(userId)).map(User::clone);
    }

    public Optional<String> getShippingCompanyIdByName(String name) {
        return shippingCompanies.values()
                .stream()
                .filter(shippingCompany -> shippingCompany.getName().equals(name))
                .findFirst()
                .map(ShippingCompany::getId);
    }

    public List<Order> getShippingCompanyInitializedOrders(String shippingCompanyId) {
        return shippingCompanies.get(shippingCompanyId).getInitializedOrders();
    }

    public List<Order> getShippingCompanyExpeditedOrders(String shippingCompanyId) {
        return shippingCompanies.get(shippingCompanyId).getExpeditedOrders();
    }

    public List<Order> getUserReturnableOrders(String userId) {
        return users.get(userId).getReturnableOrders();
    }

    public void deliverOrder(String orderId) {
        orders.get(orderId).deliver();
    }

    public void expediteOrder(String orderId) {
        orders.get(orderId).expedite();
    }

    public void orderUserCart(String buyerId) throws ProductInCartUnavailable {
        // get buying user
        User buyer = users.get(buyerId);

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
        record SellerShippingCompanyPair(String sellerId, String shippingCompanyId) {
        }

        // generate and distribute orders
        cart.stream()
                .collect(Collectors.groupingBy(p -> new SellerShippingCompanyPair(p.getSellerId(), p.getShippingCompanyId())))
                .entrySet()
                .stream()
                .map(e -> new Order(e.getValue(), buyerId, e.getKey().sellerId, e.getKey().shippingCompanyId, shippingCompanies.get(e.getKey().shippingCompanyId)
                        .shippingCost(e.getValue().size())))
                .forEach(order -> {
                    orders.put(order.getId(), order);
                    buyer.addOrderMade(order);
                    users.get(order.getSellerId()).addOrderReceived(order);
                    shippingCompanies.get(order.getShippingCompanyId()).addOrder(order);
                    revenue = revenue.add(order.vintageFees());
                });
    }

    public void returnOrder(String orderId) {
        if (orders.containsKey(orderId)) {
            Order returnedOrder = orders.get(orderId);
            returnedOrder.setAsReturned();
            users.get(returnedOrder.getBuyerId()).orderMadeReturned(orderId);
            users.get(returnedOrder.getSellerId()).orderReceivedReturned(orderId);
        }
    }

    public Optional<User> userWithMostRevenue() {
        return users.values().stream().max(Comparator.comparing(User::getRevenue));
    }

    public Optional<User> userWithMostRevenue(LocalDateTime from, LocalDateTime to) {
        return users.values().stream().max(Comparator.comparing(user -> user.getRevenue(from, to))).map(User::clone);
    }

    public Optional<ShippingCompany> shippingCompanyMostRevenue() {
        return shippingCompanies.values().stream().max(Comparator.comparing(ShippingCompany::getRevenue));
    }

    public List<Order> userIssuedOrders(String userId) {
        return users.get(userId).getOrdersMade().stream().map(Order::clone).toList();
    }

    public List<User> topSellersInInterval(LocalDateTime from, LocalDateTime to) {
        return users.values().stream().sorted(Comparator.comparing(user -> user.getRevenue(from, to))).toList();
    }

    public List<User> topBuyersInInterval(LocalDateTime from, LocalDateTime to) {
        return users.values().stream().sorted(Comparator.comparing(user -> user.getSpending(from, to))).toList();
    }

    public BigDecimal getRevenue() {
        return revenue;
    }
}
