package controller;

import model.*;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private static final int EXIT = 0;

    private static final int NEW_SYSTEM = 1;
    private static final int LOAD_SYSTEM = 2;

    private static final int REGISTER_USER = 1;
    private static final int REGISTER_SHIPPING_COMPANY = 2;
    private static final int LOGIN = 3;
    private static final int IMPORT_DATA = 4;
    private static final int SAVE_SYSTEM = 5;

    private static final int PUBLISH_PRODUCT = 1;
    private static final int ADD_PRODUCT_TO_CART = 2;
    private static final int ORDER_CART = 3;
    private static final int RETURN_ORDER = 4;

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

    public Controller() {
        this.sc = new Scanner(System.in);
        this.userId = null;

        System.out.println("VINTAGE");
        System.out.println("  1. New System");
        System.out.println("  2. Load System");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case NEW_SYSTEM -> {
                System.out.println("\nSystem Initialization");
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
                this.model = null;
            }
            default -> {
                this.model = null;
            }
        }
    }

    public void run() {
        System.out.println("\nVintage Main Menu");
        System.out.println("  1. Register user");
        System.out.println("  2. Register shipping company");
        System.out.println("  3. User login");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case REGISTER_USER -> {
                signupUser();
                run();
            }
            case REGISTER_SHIPPING_COMPANY -> {
                signupShippingCompany();
                run();
            }
            case LOGIN -> {
                login();
                run();
            }
            /*
            case IMPORT_DATA -> {
                this.model = Parse.importData(LOG_FILE);
                IO.info("Imported a total of " + this.model.getSize() + " between transports, clients and owners");
                Input.getEnter();
                run();
            }
            case LOAD_DATA -> {
                try {
                    this.model = this.model.load();
                } catch (ClassNotFoundException | IOException e) {
                    IO.error(e.getMessage());
                }
                Input.getEnter();
                this.run();
            }
            case SAVE_SYSTEM -> {
                try {
                    this.model.save();
                } catch (IOException e) {
                    IO.error(e.getMessage());
                }
                Input.getEnter();
                this.run();
            }
            case EXIT -> {
                option = this.gui.list("Do you want to save?", IO.opcoesMenuAcceptDecline);
                if (option == 0) { // Accepts
                    try {
                        this.model.save();

                    } catch (IOException e) {
                        IO.error("Saving was not possible: " + e.getMessage());
                    }
                }
                System.exit(0);
            }
             */
        }
    }

    private void signupUser() {
        System.out.println("\nUser Signup");
        System.out.print("  Email: ");
        String email = sc.nextLine();
        System.out.print("  Name: ");
        String name = sc.nextLine();
        System.out.print("  Address: ");
        String address = sc.nextLine();
        System.out.print("  Tax number: ");
        String taxNumber = sc.nextLine();
        model.registerUser(email, name, address, taxNumber);
        System.out.println("User " + name + " registered.");
    }

    private void signupShippingCompany() {
        System.out.println("\nShipping Company Signup");
        System.out.print("  Name: ");
        String name = sc.nextLine();
        System.out.print("  Profit margin: ");
        BigDecimal profitMargin = new BigDecimal(sc.nextLine());
        System.out.print("  Is it a premium shipping company? [y/n] ");
        String premium = sc.nextLine();
        switch (premium) {
            case "y" -> {
                System.out.print("    Premium Tax: ");
                BigDecimal premiumTax = new BigDecimal(sc.nextLine());
                model.registerPremiumShippingCompany(name, profitMargin, premiumTax);
            }
            case "n" -> {
                model.registerShippingCompany(name, profitMargin);
            }
        }
        System.out.println("Shipping company " + name + " registered.");
    }

    private void login() {
        System.out.println("\nLogin");
        System.out.print("  Email: ");
        userId = model.getUserIdByEmail(sc.nextLine()).orElseThrow();
        userMenu();
    }

    private void userMenu() {
        System.out.println("\nUser Menu");
        System.out.println("  1. Publish product");
        System.out.println("  2. Add product to cart");
        System.out.println("  3. Order cart");
        System.out.println("  4. Return order");
        System.out.println("  0. Logout");
        System.out.print("  Answer: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case PUBLISH_PRODUCT -> {
                model.publishProduct(userId, newProduct());
                System.out.println("New product published.");
            }
            case ADD_PRODUCT_TO_CART -> {
                int index = 1;
                List<Product> products = model.getProducts().stream().filter(product -> product.getSellerId().equals(userId)).toList();
                for (var product : products) {
                    System.out.println("  " + index + ". " + product.show());
                    index++;
                }
                System.out.print("  Choose a product: ");
                int productOption = sc.nextInt();
                sc.nextLine();
                model.addProductToUserCart(userId, products.get(productOption).getId());
            }
            case EXIT -> {
                return;
            }
        }
        userMenu();
    }

    private Product newProduct() {
        System.out.println("\nNew Product");

        System.out.println("  Choose a type of product:");
        System.out.println("  1. Sneaker");
        System.out.println("  2. T-Shirt");
        System.out.println("  3. Handbag");
        System.out.print("  Answer: ");

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
        System.out.println("  1. New with tag");
        System.out.println("  2. New without tag");
        System.out.println("  3. Very good");
        System.out.println("  4. Good");
        System.out.println("  5. Satisfactory");
        System.out.print("  Answer: ");

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
            System.out.println("  " + index + ". " + shippingCompany.getName() + (shippingCompany instanceof Premium ? " P" : ""));
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
                System.out.println("  1. Plain");
                System.out.println("  2. Stripes");
                System.out.println("  3. Palm trees");
                System.out.print("  Answer: ");
                int patternOption = sc.nextInt();
                sc.nextLine();
                TShirt.Pattern pattern = switch (patternOption) {
                    case 1 -> TShirt.Pattern.PLAIN;
                    case 2 -> TShirt.Pattern.STRIPES;
                    case 3 -> TShirt.Pattern.PALM_TREES;
                    default -> TShirt.Pattern.PLAIN;
                };
                return new TShirt(userId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, size, pattern);
            }
            case HANDBAG -> {
                System.out.print("  Dimension in litters: ");
                BigDecimal dimension = new BigDecimal(sc.nextLine());
                System.out.print("  Material: ");
                System.out.println("  1.  Canvas");
                System.out.println("  2.  Cotton");
                System.out.println("  3.  Denim");
                System.out.println("  4.  Fabric");
                System.out.println("  5.  Leather");
                System.out.println("  6.  Nylon");
                System.out.println("  7.  Raffia");
                System.out.println("  8.  Straw");
                System.out.println("  9.  Vegan leather");
                System.out.println("  10. Velvet");
                System.out.println("  11. Vinyl");
                System.out.print("  Answer: ");

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
        }
        return null;
    }
}
