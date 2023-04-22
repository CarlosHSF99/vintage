package model;

import java.math.BigDecimal;

/**
 * TShirt class
 */
public class TShirt extends Product {
    private Size size;
    private Pattern pattern;

    /**
     * Parameterized constructor
     *
     * @param seller                 Seller identification code
     * @param description            TShirt description
     * @param brand                  TShirt brand
     * @param basePrice              TShirt base price
     * @param priceCorrection        TShirt price correction
     * @param numberOfPreviousOwners TShirt number of previous owners
     * @param state                  TShirt state
     * @param premium                TShirt premium status
     * @param shippingCompany        TShirt assigned shipping company
     * @param size                   TShirt size
     * @param pattern                TShirt pattern
     */
    public TShirt(String seller, String description, String brand, String basePrice, String priceCorrection, int numberOfPreviousOwners, int state, boolean premium, ShippingCompany shippingCompany, Size size, Pattern pattern) {
        super(seller, description, brand, basePrice, priceCorrection, numberOfPreviousOwners, state, premium, shippingCompany);
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

    public void setSize(Size size) {
        this.size = size;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public BigDecimal price() {
        BigDecimal discount = getPriceCorrection().add(isUsed() ? pattern.getValue() : BigDecimal.ZERO);
        if (discount.compareTo(BigDecimal.ONE) >= 0) {
            return BigDecimal.ZERO;
        }
        return getBasePrice().multiply(BigDecimal.ONE.subtract(discount));
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

    enum Pattern {
        PLAIN("0.0"), STRIPES("0.5"), PALM_TREES("0.5");

        private final BigDecimal value;

        Pattern(String value) {
            this.value = new BigDecimal(value);
        }

        public BigDecimal getValue() {
            return value;
        }
    }
}