package exceptions;

public class EmptyOrderException extends IllegalStateException {
    public EmptyOrderException(String message) {
        super(message);
    }
}
