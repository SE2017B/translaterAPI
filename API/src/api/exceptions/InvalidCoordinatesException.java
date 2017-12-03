package api.exceptions;

public class InvalidCoordinatesException extends ServiceException {
    public InvalidCoordinatesException(final int x, final int y) {
        super("Invalid starting coordinates: x = " + x + "; y = " + y);
    }
}
