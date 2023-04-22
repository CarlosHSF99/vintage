package model;

import java.math.BigDecimal;

/**
 * Abstract product class.
 */
public abstract class Product {
    private static long numberOfProducts = 0;

    private final String code;
    private final String seller;
    private String description;
    private String brand;
    private BigDecimal basePrice;
    private BigDecimal priceCorrection;
    private int numberOfPreviousOwners;
    private int state;
    private boolean premium;
    private ShippingCompany shippingCompany;

    /**
     * Parameterized constructor.
     *
     * @param seller                 Seller identification code
     * @param description            Product description
     * @param brand                  Product brand
     * @param basePrice              Product base price
     * @param priceCorrection        Product price correction
     * @param numberOfPreviousOwners Product number of previous owners
     * @param state                  Product state
     * @param premium                Product premium status
     * @param shippingCompany        Product assigned shipping company
     */
    public Product(String seller, String description, String brand, String basePrice, String priceCorrection, int numberOfPreviousOwners, int state, boolean premium, ShippingCompany shippingCompany) {
        this.code = nextAlphanumericCode();
        this.seller = seller;
        this.description = description;
        this.brand = brand;
        this.basePrice = new BigDecimal(basePrice);
        this.priceCorrection = new BigDecimal(priceCorrection);
        this.numberOfPreviousOwners = numberOfPreviousOwners;
        this.state = state;
        this.premium = premium;
        this.shippingCompany = shippingCompany;
    }

    /**
     * Copy constructor.
     *
     * @param other Other Product
     */
    public Product(Product other) {
        this.code = nextAlphanumericCode();
        this.seller = other.seller;
        this.description = other.description;
        this.brand = other.brand;
        this.basePrice = other.basePrice;
        this.priceCorrection = other.priceCorrection;
        this.numberOfPreviousOwners = other.numberOfPreviousOwners;
        this.state = other.state;
        this.premium = other.premium;
        this.shippingCompany = other.shippingCompany;
    }

    public String getCode() {
        return code;
    }

    public String getSeller() {
        return seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = new BigDecimal(basePrice);
    }

    public BigDecimal getPriceCorrection() {
        return priceCorrection;
    }

    public void setPriceCorrection(String priceCorrection) {
        this.priceCorrection = new BigDecimal(priceCorrection);
    }

    public int getNumberOfPreviousOwners() {
        return numberOfPreviousOwners;
    }

    public void setNumberOfPreviousOwners(int numberOfPreviousOwners) {
        this.numberOfPreviousOwners = numberOfPreviousOwners;
    }

    public void incrementNumberOfPreviousOwners() {
        numberOfPreviousOwners++;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public ShippingCompany getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(ShippingCompany shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    /**
     * Checks if the Product is new.
     *
     * @return Returns whether the Product is new
     */
    public boolean isNew() {
        return numberOfPreviousOwners == 0;
    }

    /**
     * Checks if the Product is used.
     *
     * @return Returns whether the Product is used.
     */
    public boolean isUsed() {
        return numberOfPreviousOwners > 0;
    }

    public abstract BigDecimal price();

    private String nextAlphanumericCode() {
        return String.format("%8s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "code='" + code + '\'' +
                ", seller='" + seller + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", basePrice=" + basePrice +
                ", priceCorrection=" + priceCorrection +
                ", numberOfPreviousOwners=" + numberOfPreviousOwners +
                ", state=" + state +
                ", premium=" + premium +
                ", shippingCompany=" + shippingCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (numberOfPreviousOwners != product.numberOfPreviousOwners) return false;
        if (state != product.state) return false;
        if (premium != product.premium) return false;
        if (!code.equals(product.code)) return false;
        if (!seller.equals(product.seller)) return false;
        if (!description.equals(product.description)) return false;
        if (!brand.equals(product.brand)) return false;
        if (!basePrice.equals(product.basePrice)) return false;
        if (!priceCorrection.equals(product.priceCorrection)) return false;
        return shippingCompany.equals(product.shippingCompany);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + seller.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + basePrice.hashCode();
        result = 31 * result + priceCorrection.hashCode();
        result = 31 * result + numberOfPreviousOwners;
        result = 31 * result + state;
        result = 31 * result + (premium ? 1 : 0);
        result = 31 * result + shippingCompany.hashCode();
        return result;
    }

    @Override
    public abstract Product clone();
}
