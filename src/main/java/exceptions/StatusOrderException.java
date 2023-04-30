package exceptions;

public class StatusOrderException extends IllegalStateException {
    public StatusOrderException(String message) {
        super(message);
    }
}
