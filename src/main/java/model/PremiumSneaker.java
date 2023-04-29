package model;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class PremiumSneaker extends Sneaker implements Premium {
    public PremiumSneaker(String seller, String description, String brand, String basePrice, int numberOfPreviousOwners, State state, ShippingCompany shippingCompany, int size, Color color, boolean laces, Year collectionYear, BigDecimal appreciation) {
        super(seller, description, brand, basePrice, numberOfPreviousOwners, state, shippingCompany, size, color, laces, collectionYear, appreciation);
    }

    public PremiumSneaker(Sneaker other) {
        super(other);
    }

    @Override
    public BigDecimal priceCorrection() {
        return BigDecimal.ONE.add(getSellerPriceCorrection())
                .pow((int) getCollectionYear().until(Year.now(), ChronoUnit.YEARS));
    }
}
