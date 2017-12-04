package api;

import api.exceptions.InvalidCoordinatesException;
import api.exceptions.InvalidWindowDimensionsException;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

class SampleServicePreconditions {
    private static final int MIN_WINDOW_WIDTH = 200;
    private static final int MIN_WINDOW_HEIGHT = 400;

    private SampleServicePreconditions() {

    }

    private static void throwInvalidStartingCoordinatesException(final int xCoord, final int yCoord) {
        throw new InvalidCoordinatesException(xCoord, yCoord);
    }

    static void verifyStartingCoordinates(final int xCoord, final int yCoord) {
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        if (xCoord < primaryScreenBounds.getMinX() ||
            yCoord < primaryScreenBounds.getMinY() ||
            xCoord > primaryScreenBounds.getMaxX() ||
            yCoord > primaryScreenBounds.getMaxY()) {
            SampleServicePreconditions.throwInvalidStartingCoordinatesException(xCoord, yCoord);
        }
    }

    private static void throwInvalidWindowDimensionsException(final int windowHeight, final int windowWidth) {
        throw new InvalidWindowDimensionsException(windowHeight, windowWidth);
    }

    static void verifyWindowDimensions(final int windowHeight, final int windowWidth) {
        if (windowWidth < SampleServicePreconditions.MIN_WINDOW_WIDTH ||
            windowHeight < SampleServicePreconditions.MIN_WINDOW_HEIGHT) {
            SampleServicePreconditions.throwInvalidWindowDimensionsException(windowHeight, windowWidth);
        }
    }
}
