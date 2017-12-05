package api;

import Controllers.RequestController;
import api.exceptions.ServiceException;
import com.sun.org.apache.regexp.internal.RE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        RequestController.setLocation(destNodeID);
        System.out.println("СУКА БЛЯ");
    }

    private void showServiceWindow(final int xcoord, final int ycoord, final int windowWidth, final int windowLength) {
        Parent root;
        System.out.println(".3");
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/RequestAPI.fxml"));
            System.out.println(".2");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("1");
        primaryStage.setTitle("Make New Translation Request");
        System.out.println("2");
        primaryStage.setScene(new Scene(root, windowWidth, windowLength));
        System.out.println("3");
        primaryStage.setX(xcoord);
        System.out.println("4");
        primaryStage.setY(ycoord);
        System.out.println("5");
        primaryStage.show();
        System.out.println("6");
//        RequestController.onShow();
//        RequestController.init();
    }

    public static SampleService newInstance(final Stage primaryStage) {
        return new SampleService(primaryStage);
    }
}
