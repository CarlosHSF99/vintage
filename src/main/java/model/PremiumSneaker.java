package model;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Year;
import java.time.temporal.ChronoUnit;

/**
 * Premium Sneaker class
 */
public class PremiumSneaker extends Sneaker implements Premium {
    /**
     * Parameterized constructor.
     *
     * @param sellerId               Seller identification code
     * @param shippingCompanyId      Sneaker assigned shipping company
     * @param description            Sneaker description
     * @param brand                  Sneaker brand
     * @param basePrice              Sneaker base price
     * @param numberOfPreviousOwners Sneaker number of previous owners
     * @param state                  Sneaker state
     * @param size                   Sneaker size
     * @param color                  Sneaker color
     * @param laces                  Sneaker laces
     * @param collectionYear         Sneaker collection year
     * @param appreciation           Sneaker appreciation defined by seller
     */
    public PremiumSneaker(String sellerId, String shippingCompanyId, String description, String brand, BigDecimal basePrice, int numberOfPreviousOwners, State state, int size, Color color, boolean laces, Year collectionYear, BigDecimal appreciation) {
        super(sellerId, shippingCompanyId, description, brand, basePrice, numberOfPreviousOwners, state, size, color, laces, collectionYear, appreciation);
    }

    /**
     * Copy constructor.
     *
     * @param other PremiumSneaker
     */
    public PremiumSneaker(Sneaker other) {
        super(other);
    }

    /**
     * Returns the price correction.
     * Price correction is calculated with the appreciation rate set by the seller.
     *
     * @return price correction
     */
    @Override
    public BigDecimal priceCorrection() {
        return BigDecimal.ONE.add(getSellerPriceCorrection())
                .pow((int) getCollectionYear().until(Year.now(), ChronoUnit.YEARS));
    }

    @Override
    public String show() {
        return null;
    }

}
