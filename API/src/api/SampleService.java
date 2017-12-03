package api;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import teamh.api.exceptions.ServiceException;

import java.io.IOException;

public class SampleService implements ExportableServiceComponent {
    private final Stage primaryStage;

    private SampleService(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String destNodeID, String originNodeID) throws ServiceException {
        SampleServicePreconditions.verifyStartingCoordinates(xcoord, ycoord);
        SampleServicePreconditions.verifyWindowDimensions(windowLength, windowWidth);
        // TODO: Run other precondition checks here

        // All checks passed; spawn the window
        showServiceWindow(xcoord, ycoord, windowWidth, windowLength);
    }

    private void showServiceWindow(final int xcoord, final int ycoord, final int windowWidth, final int windowLength) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
        } catch (IOException e) {
            return;
        }

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, windowWidth, windowLength));
        primaryStage.setX(xcoord);
        primaryStage.setY(ycoord);
        primaryStage.show();
    }

    public static SampleService newInstance(final Stage primaryStage) {
        return new SampleService(primaryStage);
    }
}
