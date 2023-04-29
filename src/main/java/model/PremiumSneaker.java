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
     * @param seller                 Seller identification code
     * @param description            Sneaker description
     * @param brand                  Sneaker brand
     * @param basePrice              Sneaker base price
     * @param numberOfPreviousOwners Sneaker number of previous owners
     * @param state                  Sneaker state
     * @param shippingCompany        Sneaker assigned shipping company
     * @param size                   Sneaker size
     * @param color                  Sneaker color
     * @param laces                  Sneaker laces
     * @param collectionYear         Sneaker collection year
     * @param appreciation           Sneaker appreciation defined by seller
     */
    public PremiumSneaker(String seller, String description, String brand, String basePrice, int numberOfPreviousOwners, State state, ShippingCompany shippingCompany, int size, Color color, boolean laces, Year collectionYear, String appreciation) {
        super(seller, description, brand, basePrice, numberOfPreviousOwners, state, shippingCompany, size, color, laces, collectionYear, appreciation);
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
}
