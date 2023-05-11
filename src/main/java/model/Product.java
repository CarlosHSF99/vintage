package model;

import java.math.BigDecimal;

/**
 * Abstract product class.
 */
public abstract class Product {
    private static long numberOfProducts = 0;

    private final String id;
    private final String sellerId;
    private final String shippingCompanyId;
    private final String description;
    private final String brand;
    private final BigDecimal basePrice;
    private final int numberOfPreviousOwners;
    private final State state;

    /**
     * Parameterized constructor.
     *
     * @param sellerId               Seller identification code
     * @param shippingCompanyId      Product assigned shipping company
     * @param description            Product description
     * @param brand                  Product brand
     * @param basePrice              Product base price
     * @param numberOfPreviousOwners Product number of previous owners
     * @param state                  Product state
     */
    public Product(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state) {
        this.id = nextAlphanumericId();
        this.sellerId = sellerId;
        this.shippingCompanyId = shippingCompanyId;
        this.description = description;
        this.brand = brand;
        this.basePrice = basePrice;
        this.numberOfPreviousOwners = numberOfPreviousOwners;
        this.state = state;
    }

    /**
     * Copy constructor.
     *
     * @param other Other Product
     */
    public Product(Product other) {
        this.id = nextAlphanumericId();
        this.sellerId = other.sellerId;
        this.shippingCompanyId = other.shippingCompanyId;
        this.description = other.description;
        this.brand = other.brand;
        this.basePrice = other.basePrice;
        this.numberOfPreviousOwners = other.numberOfPreviousOwners;
        this.state = other.state;
    }

    public String getId() {
        return id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getShippingCompanyId() {
        return shippingCompanyId;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public int getNumberOfPreviousOwners() {
        return numberOfPreviousOwners;
    }

    public State getState() {
        return state;
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

    private String nextAlphanumericId() {
        return String.format("%8s", Long.toString(numberOfProducts++, 36)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", shippingCompanyId='" + shippingCompanyId + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", basePrice=" + basePrice +
                ", numberOfPreviousOwners=" + numberOfPreviousOwners +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (numberOfPreviousOwners != product.numberOfPreviousOwners) return false;
        if (!id.equals(product.id)) return false;
        if (!sellerId.equals(product.sellerId)) return false;
        if (!shippingCompanyId.equals(product.shippingCompanyId)) return false;
        if (!description.equals(product.description)) return false;
        if (!brand.equals(product.brand)) return false;
        if (!basePrice.equals(product.basePrice)) return false;
        return state == product.state;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sellerId.hashCode();
        result = 31 * result + shippingCompanyId.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + basePrice.hashCode();
        result = 31 * result + numberOfPreviousOwners;
        result = 31 * result + state.hashCode();
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
