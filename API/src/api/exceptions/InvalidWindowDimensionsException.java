package api.exceptions;

public class InvalidWindowDimensionsException extends ServiceException {
    public InvalidWindowDimensionsException(final int windowHeight, final int windowWidth) {
        super("Invalid Window Dimensions: width = " + windowWidth + "; height = " + windowHeight);
    }
}
