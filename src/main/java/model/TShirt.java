package model;

import java.math.BigDecimal;

/**
 * TShirt class
 */
public class TShirt extends Product {
    private final Size size;
    private final Pattern pattern;

    /**
     * Parameterized constructor
     *
     * @param sellerId               Seller identification code
     * @param shippingCompanyId      TShirt assigned shipping company
     * @param description            TShirt description
     * @param brand                  TShirt brand
     * @param basePrice              TShirt base price
     * @param numberOfPreviousOwners TShirt number of previous owners
     * @param state                  TShirt state
     * @param size                   TShirt size
     * @param pattern                TShirt pattern
     */
    public TShirt(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state, Size size, Pattern pattern) {
        super(sellerId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state);
        this.size = size;
        this.pattern = pattern;
    }

    /**
     * Copy constructor
     *
     * @param other Other TShirt
     */
    public TShirt(TShirt other) {
        super(other);
        this.size = other.size;
        this.pattern = other.pattern;
    }

    public Size getSize() {
        return size;
    }

    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Returns the price correction.
     *
     * @return price correction
     */
    public BigDecimal priceCorrection() {
        return isUsed() ? pattern.getValue() : BigDecimal.ONE;
    }

    @Override
    public String show() {
        return "T-Shirt | " + getBrand() + " | " + getState() + " | " + getDescription() + " | " + getPattern() + " | Price: " + price() + " | nº prev. owners: " + getNumberOfPreviousOwners();
    }

    @Override
    public String toString() {
        return "TShirt{" +
                super.toString() +
                ", size=" + size +
                ", pattern=" + pattern +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TShirt tShirt = (TShirt) o;

        if (size != tShirt.size) return false;
        return pattern == tShirt.pattern;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + pattern.hashCode();
        return result;
    }

    @Override
    public TShirt clone() {
        return new TShirt(this);
    }

    public enum Pattern {
        PLAIN("1.0"), STRIPES("0.5"), PALM_TREES("0.5");

        private final BigDecimal value;

        Pattern(String value) {
            this.value = new BigDecimal(value);
        }

        public BigDecimal getValue() {
            return value;
        }
    }
}
