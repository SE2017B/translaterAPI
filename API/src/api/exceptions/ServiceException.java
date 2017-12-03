package api.exceptions;

public abstract class ServiceException extends RuntimeException {
    ServiceException(String message) {
        super(message);
    }
}
