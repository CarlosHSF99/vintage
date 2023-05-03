package exceptions;

public class LateReturnException extends IllegalStateException {
    public LateReturnException(String message) {
        super(message);
    }
}
