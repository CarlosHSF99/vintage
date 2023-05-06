package model;

import java.math.BigDecimal;

/**
 * Abstract product class.
 */
public abstract class Product {
    private static long numberOfProducts = 0;

    private final String id;
    private final String sellerCode;
    private String description;
    private String brand;
    private BigDecimal basePrice;
    private int numberOfPreviousOwners;
    private State state;
    private ShippingCompany shippingCompany;

    /**
     * Parameterized constructor.
     *
     * @param sellerCode                 Seller identification code
     * @param description            Product description
     * @param brand                  Product brand
     * @param basePrice              Product base price
     * @param numberOfPreviousOwners Product number of previous owners
     * @param state                  Product state
     * @param shippingCompany        Product assigned shipping company
     */
    public Product(String sellerCode, String description, String brand, String basePrice, int numberOfPreviousOwners, State state, ShippingCompany shippingCompany) {
        this.id = nextAlphanumericId();
        this.sellerCode = sellerCode;
        this.description = description;
        this.brand = brand;
        this.basePrice = new BigDecimal(basePrice);
        this.numberOfPreviousOwners = numberOfPreviousOwners;
        this.state = state;
        this.shippingCompany = shippingCompany;
    }

    /**
     * Copy constructor.
     *
     * @param other Other Product
     */
    public Product(Product other) {
        this.id = nextAlphanumericId();
        this.sellerCode = other.sellerCode;
        this.description = other.description;
        this.brand = other.brand;
        this.basePrice = other.basePrice;
        this.numberOfPreviousOwners = other.numberOfPreviousOwners;
        this.state = other.state;
        this.shippingCompany = other.shippingCompany;
    }

    public String getId() {
        return id;
    }

    public String getSellerCode() {
        return sellerCode;
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

    public int getNumberOfPreviousOwners() {
        return numberOfPreviousOwners;
    }

    public void setNumberOfPreviousOwners(int numberOfPreviousOwners) {
        this.numberOfPreviousOwners = numberOfPreviousOwners;
    }

    public void incrementNumberOfPreviousOwners() {
        numberOfPreviousOwners++;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
     * @return Returns whether the Product is used
     */
    public boolean isUsed() {
        return numberOfPreviousOwners > 0;
    }

    /**
     * Returns the price correction of the Product as a BigDecimal.
     * The price method calculates the final price of a Product by multiplying its basePrice by the return value of this method.
     *
     * @return Price correction of Product as a BigDecimal
     */
    public abstract BigDecimal priceCorrection();

    /**
     * Returns the price of the Product as a BigDecimal.
     *
     * @return Price of Product as a BigDecimal
     */
    public BigDecimal price() {
        return basePrice.multiply(priceCorrection());
    }

    public BigDecimal shippingPrice() {
        return null;
    }

    private String nextAlphanumericId() {
        return String.format("%8s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "code='" + id + '\'' +
                ", seller='" + sellerCode + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", basePrice=" + basePrice +
                ", numberOfPreviousOwners=" + numberOfPreviousOwners +
                ", state=" + state +
                ", shippingCompany=" + shippingCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (numberOfPreviousOwners != product.numberOfPreviousOwners) return false;
        if (!id.equals(product.id)) return false;
        if (!sellerCode.equals(product.sellerCode)) return false;
        if (!description.equals(product.description)) return false;
        if (!brand.equals(product.brand)) return false;
        if (!basePrice.equals(product.basePrice)) return false;
        if (state != product.state) return false;
        return shippingCompany.equals(product.shippingCompany);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sellerCode.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + basePrice.hashCode();
        result = 31 * result + numberOfPreviousOwners;
        result = 31 * result + state.hashCode();
        result = 31 * result + shippingCompany.hashCode();
        return result;
    }

    @Override
    public abstract Product clone();

    /**
     * State enum
     */
    enum State {
        NEW_WITH_TAG("1.0"),
        NEW_WITHOUT_TAG("0.9"),
        VERY_GOOD("0.8"),
        GOOD("0.7"),
        SATISFACTORY("0.6");

        private final BigDecimal priceCorrection;

        State(String priceCorrection) {
            this.priceCorrection = new BigDecimal(priceCorrection);
        }

        public BigDecimal getPriceCorrection() {
            return priceCorrection;
        }
    }
}
