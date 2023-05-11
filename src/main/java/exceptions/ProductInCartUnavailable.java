package exceptions;

public class ProductInCartUnavailable extends IllegalStateException {
    public ProductInCartUnavailable(String message) {
        super(message);
    }
}
