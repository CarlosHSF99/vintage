package controllerView;

import model.*;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Scanner;

public class ControllerView {
    private static final int EXIT = 0;

    private static final int NEW_SYSTEM = 1;
    private static final int LOAD_SYSTEM = 2;

    private static final int REGISTER_USER = 1;
    private static final int REGISTER_SHIPPING_COMPANY = 2;
    private static final int USER_LOGIN = 3;
    private static final int SHIPPING_COMPANY_LOGIN = 4;
    private static final int SAVE_SYSTEM = 5;
    private static final int STATISTICS = 6;
    private static final int ADVANCE_TIME = 7;

    private static final int ALL_TIME_BEST_SELLER = 1;
    private static final int BEST_SELLER_IN_TIME_INTERVAL = 2;
    private static final int BEST_SHIPPING_COMPANY = 3;
    private static final int USER_ISSUED_ORDERS = 4;
    private static final int USERS_BY_REVENUE = 5;
    private static final int USERS_BY_SPENDING = 6;
    private static final int VINTAGE_REVENUE = 7;

    private static final int PUBLISH_PRODUCT = 1;
    private static final int ADD_PRODUCT_TO_CART = 2;
    private static final int REMOVE_PRODUCT_FROM_CART = 3;
    private static final int ORDER_CART = 4;
    private static final int RETURN_ORDER = 5;
    private static final int SHOW_CART = 6;

    private static final int EXPEDITE = 1;
    private static final int DELIVER = 2;
    private static final int PROFIT_MARGIN = 3;

    private static final int SNEAKER = 1;
    private static final int T_SHIRT = 2;
    private static final int HANDBAG = 3;

    private static final int PLAIN = 1;
    private static final int STRIPES = 2;
    private static final int PALM_TREES = 3;

    private static final int CANVAS = 1;
    private static final int COTTON = 2;
    private static final int DENIM = 3;
    private static final int FABRIC = 4;
    private static final int LEATHER = 5;
    private static final int NYLON = 6;
    private static final int RAFFIA = 7;
    private static final int STRAW = 8;
    private static final int VEGAN_LEATHER = 9;
    private static final int VELVET = 10;
    private static final int VINYL = 11;

    private final Vintage model;
    private final Scanner sc;
    private String userId;
    private String shippingCompanyId;

    public ControllerView() {
        this.sc = new Scanner(System.in);
        this.userId = null;

        System.out.println("VINTAGE - " + now());
        System.out.println("  1. New System");
        System.out.println("  2. Load System");
        System.out.println("  0. Exit");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case NEW_SYSTEM -> {
                System.out.println("\nSystem Initialization - " + now());
                System.out.print("  Small order expedition base cost: ");
                var small = new BigDecimal(sc.nextLine());
                System.out.print("  Medium order expedition base cost: ");
                var medium = new BigDecimal(sc.nextLine());
                System.out.print("  Big order expedition base cost: ");
                var big = new BigDecimal(sc.nextLine());
                System.out.print("  Transportation fee: ");
                var fee = new BigDecimal(sc.nextLine());
                this.model = new Vintage(small, medium, big, fee);
            }
            case LOAD_SYSTEM -> {
                System.out.println("\nLoad System State - " + now());
                System.out.print("  Path: ");
                String path = sc.nextLine();
                ClockVintagePair systemState = null;
                try {
                    FileInputStream fileIn = new FileInputStream(path);
                    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                    systemState = (ClockVintagePair) objectIn.readObject();
                    objectIn.close();
                    fileIn.close();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("  Error loading system from file.");
                }
                this.model = systemState.vintage;
                TimeSimulation.setClock(systemState.clock);
                System.out.println("  System loaded from " + path);
            }
            case EXIT -> {
                this.model = null;
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> {
                this.model = null;
            }
        }
    }

    public void run() {
        System.out.println("\nVintage Main Menu - " + now());
        System.out.println("  1. Register user");
        System.out.println("  2. Register shipping company");
        System.out.println("  3. User login");
        System.out.println("  4. Shipping company login");
        System.out.println("  5. Save system state");
        System.out.println("  6. Statistics");
        System.out.println("  7. Advance time");
        System.out.println("  0. Exit");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case REGISTER_USER -> signupUser();
            case REGISTER_SHIPPING_COMPANY -> signupShippingCompany();
            case USER_LOGIN -> userLogin();
            case SHIPPING_COMPANY_LOGIN -> shippingCompanyLogin();
            case SAVE_SYSTEM -> saveSystemState();
            case STATISTICS -> statisticsMenu();
            case ADVANCE_TIME -> advanceTime();
            case EXIT -> exit();
            default -> {
            }
        }

        run();
    }

    private void statisticsMenu() {
        System.out.println("\nExiting - " + now());
        System.out.println("  1. All time seller with most revenue");
        System.out.println("  2. Seller with most revenue in time interval");
        System.out.println("  3. Shipping company with most revenue");
        System.out.println("  4. Orders issued by user");
        System.out.println("  5. List sorted users by revenue");
        System.out.println("  6. List sorted users by spending");
        System.out.println("  7. Vintage revenue");
        System.out.println("  0. Back");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case ALL_TIME_BEST_SELLER -> {
                System.out.print("  All time best seller: " + model.userWithMostRevenue().orElseThrow().getName());
            }
            case BEST_SELLER_IN_TIME_INTERVAL -> {
                System.out.print("  From: ");
                LocalDateTime from = LocalDateTime.parse(sc.nextLine());
                System.out.print("  To: ");
                LocalDateTime to = LocalDateTime.parse(sc.nextLine());
                System.out.println("  Best seller between " + from.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)) + " and " + to.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)) + ": " + model.userWithMostRevenue(from, to));
            }
            case BEST_SHIPPING_COMPANY -> {
                System.out.println("  Shipping company with most revenue: " + model.shippingCompanyMostRevenue());
            }
            case USER_ISSUED_ORDERS -> {
                System.out.print("  User email: ");
                String email = sc.nextLine();
                List<Order> orders = model.getUser(model.getUserIdByEmail(email).orElseThrow()).orElseThrow().getOrdersReceived();
                for (var order : orders) {
                    System.out.println("  " + order);
                }
            }
            case USERS_BY_REVENUE -> {
                System.out.print("  From: ");
                LocalDateTime from = LocalDateTime.parse(sc.nextLine());
                System.out.print("  To: ");
                LocalDateTime to = LocalDateTime.parse(sc.nextLine());
                System.out.println("  Users by revenue between " + from.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)) + " and " + to.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)) + ":");
                int index = 1;
                for (var user : model.topSellersInInterval(from, to)) {
                    System.out.println("  " + index + ". " + user.getName() + " - " + user.getRevenue() + " €");
                    index++;
                }
            }
            case USERS_BY_SPENDING -> {
                System.out.print("  From: ");
                LocalDateTime from = LocalDateTime.parse(sc.nextLine());
                System.out.print("  To: ");
                LocalDateTime to = LocalDateTime.parse(sc.nextLine());
                System.out.println("  Users by spending between " + from.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)) + " and " + to.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)) + ":");
                int index = 1;
                for (var user : model.topBuyersInInterval(from, to)) {
                    System.out.println("  " + index + ". " + user.getName() + " - " + user.getSpending() + " €");
                    index++;
                }

            }
            case VINTAGE_REVENUE -> {
                System.out.println("  Vintage revenue: " + model.getRevenue());
            }
            default -> {
            }
        }
    }

    private void exit() {
        System.out.println("\nExiting - " + now());
        System.out.print("  Do you want to save before exiting? [y/n] ");
        String exitOption = sc.nextLine();
        if (exitOption.equals("y")) {
            saveSystemState();
        } else {
            System.out.println("  Exiting...");
        }
        System.exit(0);
    }

    private void advanceTime() {
        System.out.println("\nAdvance Time - " + now());
        System.out.print("  Time offset: ");
        Duration duration = Duration.parse(sc.nextLine());
        TimeSimulation.advanceTime(duration);
        long days = duration.toDays();
        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();
        int seconds = duration.toSecondsPart();
        System.out.println("  Advanced " + days + " day" + (days == 1 ? "" : "s") + ", " + hours + " hour" + (hours == 1 ? "" : "s") + ", " + minutes + " minute" + (minutes == 1 ? "" : "s") + " and " + seconds + " second" + (seconds == 1 ? "" : "s") + ".");
    }

    private void saveSystemState() {
        ClockVintagePair systemState = new ClockVintagePair(TimeSimulation.getClock(), model);
        System.out.println("\nSave System State - " + now());
        System.out.print("  Path: ");
        String path = sc.nextLine();
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(systemState);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("  Error saving the system state!");
        }
        System.out.println("  System saved to " + path + ".");
    }

    private void signupUser() {
        System.out.println("\nUser Signup - " + now());
        System.out.print("  Email: ");
        String email = sc.nextLine();
        System.out.print("  Name: ");
        String name = sc.nextLine();
        System.out.print("  Address: ");
        String address = sc.nextLine();
        System.out.print("  Tax number: ");
        String taxNumber = sc.nextLine();
        model.registerUser(email, name, address, taxNumber);
        System.out.println("  User " + name + " registered.");
    }

    private void signupShippingCompany() {
        System.out.println("\nShipping Company Signup - " + now());
        System.out.print("  Name: ");
        String name = sc.nextLine();
        System.out.print("  Profit margin: ");
        BigDecimal profitMargin = new BigDecimal(sc.nextLine());
        System.out.print("  Is it a premium shipping company? [y/n] ");
        String premium = sc.nextLine();
        if (premium.equals("y")) {
            System.out.print("    Premium Tax: ");
            BigDecimal premiumTax = new BigDecimal(sc.nextLine());
            model.registerPremiumShippingCompany(name, profitMargin, premiumTax);
        } else {
            model.registerShippingCompany(name, profitMargin);
        }
        System.out.println("  Shipping company " + name + " registered.");
    }

    private void userLogin() {
        System.out.println("\nUser Login - " + now());
        System.out.print("  Email: ");
        userId = model.getUserIdByEmail(sc.nextLine()).orElseThrow();
        userMenu();
    }

    private void shippingCompanyLogin() {
        System.out.println("\nShipping Company Login - " + now());
        System.out.print("  Name: ");
        shippingCompanyId = model.getShippingCompanyIdByName(sc.nextLine()).orElseThrow();
        shippingCompanyMenu();
    }

    private void userMenu() {
        System.out.println("\nUser Menu - " + now());
        System.out.println("  1. Publish product");
        System.out.println("  2. Add product to cart");
        System.out.println("  3. Remove product from cart");
        System.out.println("  4. Order cart");
        System.out.println("  5. Return order");
        System.out.println("  6. Show cart");
        System.out.println("  0. Logout");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case PUBLISH_PRODUCT -> publishProduct();
            case ADD_PRODUCT_TO_CART -> addProductToCart();
            case REMOVE_PRODUCT_FROM_CART -> removeProductFromCart();
            case ORDER_CART -> model.orderUserCart(userId);
            case RETURN_ORDER -> returnOrder();
            case SHOW_CART -> showCart();
            case EXIT -> {
                return;
            }
            default -> {
            }
        }

        userMenu();
    }

    private void removeProductFromCart() {
        System.out.println("  Choose a product:");
        int index = 1;
        List<Product> cart = model.getUser(userId).orElseThrow().getCart();
        for (var product : cart) {
            System.out.println("    " + index + ". " + product.show());
            index++;
        }
        System.out.println("    0. Exit");
        System.out.print("    Answer: ");
        int productOption = sc.nextInt();
        sc.nextLine();
        if (productOption == EXIT) {
            return;
        }
        model.removeProductFromUserCart(userId, cart.get(productOption - 1).getId());
    }

    private void returnOrder() {
        System.out.println("  Choose an order:");
        int index = 1;
        List<Order> returnableOrders = model.getUserReturnableOrders(userId);
        for (var order : returnableOrders) {
            User seller = model.getUser(order.getSellerId()).orElseThrow();
            System.out.println("    " + index + ". Size: " + order.getSize() + ", Date: " + order.getCreationDate() + ", Seller: " + seller.getName() + ", Price: " + order.totalCost() + " €");
            index++;
        }
        System.out.println("    0. Exit");
        System.out.print("    Answer: ");
        int returnOption = sc.nextInt();
        sc.nextLine();
        if (returnOption == EXIT) {
            return;
        }
        model.returnOrder(returnableOrders.get(returnOption - 1).getId());
        System.out.println("  Order expedited.");
    }

    private void showCart() {
        System.out.println("  Cart:");
        model.getUser(userId).orElseThrow().getCart().forEach(product -> System.out.println("    " + product.show()));
    }

    private void publishProduct() {
        model.publishProduct(userId, newProduct());
        System.out.println("  New product published.");
    }

    private void addProductToCart() {
        System.out.println("  Choose a product:");
        int index = 1;
        List<Product> products = model.getProducts()
                .stream()
                .filter(product -> !product.getSellerId().equals(userId))
                .toList();
        for (var product : products) {
            System.out.println("    " + index + ". " + product.show());
            index++;
        }
        System.out.println("    0. Exit");
        System.out.print("    Answer: ");
        int productOption = sc.nextInt();
        sc.nextLine();
        if (productOption == EXIT) {
            return;
        }
        model.addProductToUserCart(userId, products.get(productOption - 1).getId());
    }

    private void shippingCompanyMenu() {
        System.out.println("\nShipping Company Menu - " + now());
        System.out.println("  1. Expedite order");
        System.out.println("  2. Deliver order");
        System.out.println("  3. Change profit margin");
        System.out.println("  0. Logout");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case EXPEDITE -> expedite();
            case DELIVER -> deliverOrder();
            case PROFIT_MARGIN -> changeProfitMargin();
            case EXIT -> {
                return;
            }
            default -> {
            }
        }

        shippingCompanyMenu();
    }

    private void changeProfitMargin() {
        System.out.print("  New profit margin: ");
        model.setShippingCompanyProfitMargin(shippingCompanyId, new BigDecimal(sc.nextLine()));
        System.out.println("  Profit margin updated.");
    }

    private void expedite() {
        System.out.println("  Choose an order:");
        int index = 1;
        List<Order> initializedOrders = model.getShippingCompanyInitializedOrders(shippingCompanyId);
        for (var order : initializedOrders) {
            User buyer = model.getUser(order.getBuyerId()).orElseThrow();
            System.out.println("    " + index + ". Size: " + order.getSize() + ", Recipient: " + buyer.getName() + ", Address: " + buyer.getAddress());
            index++;
        }
        System.out.println("    0. Exit");
        System.out.print("    Answer: ");
        int expediteOption = sc.nextInt();
        sc.nextLine();
        if (expediteOption == EXIT) {
            return;
        }
        model.expediteOrder(initializedOrders.get(expediteOption - 1).getId());
        System.out.println("  Order expedited.");
    }

    public void deliverOrder() {
        System.out.println("  Choose an order:");
        int index = 1;
        List<Order> expeditedOrders = model.getShippingCompanyExpeditedOrders(shippingCompanyId);
        for (var order : expeditedOrders) {
            User buyer = model.getUser(order.getBuyerId()).orElseThrow();
            System.out.println("    " + index + ". Size: " + order.getSize() + ", Recipient: " + buyer.getName() + ", Address: " + buyer.getAddress());
            index++;
        }
        System.out.println("    0. Exit");
        System.out.print("    Answer: ");
        int deliverOption = sc.nextInt();
        sc.nextLine();
        if (deliverOption == EXIT) {
            return;
        }
        model.deliverOrder(expeditedOrders.get(deliverOption - 1).getId());
        System.out.println("  Order delivered.");
    }

    private Product newProduct() {
        System.out.println("\nNew Product - " + now());

        System.out.println("  Choose a type of product:");
        System.out.println("    1. Sneaker");
        System.out.println("    2. T-Shirt");
        System.out.println("    3. Handbag");
        System.out.print("    Answer: ");

        int productTypeOption = sc.nextInt();
        sc.nextLine();

        System.out.print("  Description: ");
        String description = sc.nextLine();
        System.out.print("  Brand: ");
        String brand = sc.nextLine();
        System.out.print("  Base price: ");
        BigDecimal basePrice = new BigDecimal(sc.nextLine());
        System.out.print("  Number of previous owners: ");
        int numberOfPreviousOwners = sc.nextInt();
        sc.nextLine();

        System.out.println("  State of the product:");
        System.out.println("    1. New with tag");
        System.out.println("    2. New without tag");
        System.out.println("    3. Very good");
        System.out.println("    4. Good");
        System.out.println("    5. Satisfactory");
        System.out.print("    Answer: ");

        int productStateOption = sc.nextInt();
        sc.nextLine();

        Product.State state = switch (productStateOption) {
            case 1 -> Product.State.NEW_WITH_TAG;
            case 2 -> Product.State.NEW_WITHOUT_TAG;
            case 3 -> Product.State.VERY_GOOD;
            case 4 -> Product.State.GOOD;
            case 5 -> Product.State.SATISFACTORY;
            default -> Product.State.NEW_WITH_TAG;
        };

        System.out.println("  Choose a shipping company:");
        int index = 1;
        List<ShippingCompany> shippingCompanies = model.getShippingCompanies();
        for (var shippingCompany : shippingCompanies) {
            System.out.println("    " + index + ". " + shippingCompany.getName() + (shippingCompany instanceof Premium ? " P" : ""));
            index++;
        }
        System.out.print("  Answer: ");
        int option = sc.nextInt();
        sc.nextLine();
        String shippingCompanyId = shippingCompanies.get(option - 1).getId();

        switch (productTypeOption) {
            case SNEAKER -> {
                System.out.print("  Size: ");
                int size = sc.nextInt();
                sc.nextLine();
                System.out.print("  Color hex: #");
                Color color = Color.decode("#" + sc.nextLine());
                System.out.print("  Has laces? [y/n] ");
                boolean laces = sc.nextLine().equals("y");
                System.out.print("  Collection year: ");
                Year collectionYear = Year.of(sc.nextInt());
                sc.nextLine();
                System.out.print("  Is the sneaker premium? [y/n] ");
                boolean premium = sc.nextLine().equals("y");
                if (premium) {
                    System.out.print("  Yearly appreciation rate: ");
                    BigDecimal appreciation = new BigDecimal(sc.nextLine());
                    return new PremiumSneaker(userId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, size, color, laces, collectionYear, appreciation);

                } else {
                    System.out.print("  Discount: ");
                    BigDecimal discount = new BigDecimal(sc.nextLine());
                    Sneaker foo = new Sneaker(userId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, size, color, laces, collectionYear, discount);
                    return new Sneaker(userId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, size, color, laces, collectionYear, discount);
                }
            }
            case T_SHIRT -> {
                System.out.print("  Size [S/M/L/XL]: ");
                Size size = switch (sc.nextLine()) {
                    case "S" -> Size.S;
                    case "M" -> Size.M;
                    case "L" -> Size.L;
                    case "XL" -> Size.XL;
                    default -> Size.M;
                };
                System.out.println("  Pattern:");
                System.out.println("    1. Plain");
                System.out.println("    2. Stripes");
                System.out.println("    3. Palm trees");
                System.out.print("    Answer: ");
                int patternOption = sc.nextInt();
                sc.nextLine();
                TShirt.Pattern pattern = switch (patternOption) {
                    case PLAIN -> TShirt.Pattern.PLAIN;
                    case STRIPES -> TShirt.Pattern.STRIPES;
                    case PALM_TREES -> TShirt.Pattern.PALM_TREES;
                    default -> TShirt.Pattern.PLAIN;
                };
                return new TShirt(userId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, size, pattern);
            }
            case HANDBAG -> {
                System.out.print("  Dimension in litters: ");
                BigDecimal dimension = new BigDecimal(sc.nextLine());
                System.out.println("  Material: ");
                System.out.println("    1.  Canvas");
                System.out.println("    2.  Cotton");
                System.out.println("    3.  Denim");
                System.out.println("    4.  Fabric");
                System.out.println("    5.  Leather");
                System.out.println("    6.  Nylon");
                System.out.println("    7.  Raffia");
                System.out.println("    8.  Straw");
                System.out.println("    9.  Vegan leather");
                System.out.println("    10. Velvet");
                System.out.println("    11. Vinyl");
                System.out.print("    Answer: ");

                int materialOption = sc.nextInt();
                sc.nextLine();

                Handbag.Material material = switch (materialOption) {
                    case CANVAS -> Handbag.Material.CANVAS;
                    case COTTON -> Handbag.Material.COTTON;
                    case DENIM -> Handbag.Material.DENIM;
                    case FABRIC -> Handbag.Material.FABRIC;
                    case LEATHER -> Handbag.Material.LEATHER;
                    case NYLON -> Handbag.Material.NYLON;
                    case RAFFIA -> Handbag.Material.RAFFIA;
                    case STRAW -> Handbag.Material.STRAW;
                    case VEGAN_LEATHER -> Handbag.Material.VEGAN_LEATHER;
                    case VELVET -> Handbag.Material.VELVET;
                    case VINYL -> Handbag.Material.VINYL;
                    default -> Handbag.Material.FABRIC;
                };

                System.out.print("  Collection year: ");
                Year collectionYear = Year.of(sc.nextInt());
                sc.nextLine();

                System.out.print("  Is the sneaker premium? [y/n] ");
                boolean premium = sc.nextLine().equals("y");
                if (premium) {
                    System.out.print("  Yearly appreciation rate: ");
                    BigDecimal appreciation = new BigDecimal(sc.nextLine());
                    return new PremiumHandbag(userId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, dimension, material, collectionYear, appreciation);
                } else {
                    return new Handbag(userId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, dimension, material, collectionYear);
                }
            }
            default -> {
                return newProduct();
            }
        }
    }

    private String now() {
        return LocalDateTime.now(TimeSimulation.getClock())
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    record ClockVintagePair(Clock clock, Vintage vintage) implements Serializable {
    }
}
