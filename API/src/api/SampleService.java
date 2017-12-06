package api;

import Controllers.RequestController;
import api.exceptions.ServiceException;
import database.nodeDatabase;
import database.serviceDatabase;
import database.staffDatabase;
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
        nodeDatabase.deleteNodeTable();
        staffDatabase.deleteStaffTable();
        serviceDatabase.deleteRequestsTable();

        nodeDatabase.createNodeTable();
        staffDatabase.createStaffTable();
        serviceDatabase.createServiceTable();

        new nodeDatabase().readNodeCSV("/csv/MapAnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapBnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapCnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapDnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapEnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapFnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapGnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapHnodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapInodes.csv");
        new nodeDatabase().readNodeCSV("/csv/MapWnodes.csv");
        nodeDatabase.insertNodesFromCSV();

        new staffDatabase().readStaffCSV("/csv/staffMembers.csv");
        staffDatabase.insertStaffFromCSV();


        nodeDatabase.outputNodesCSV();
        staffDatabase.outputStaffCSV();
        showServiceWindow(xcoord, ycoord, windowWidth, windowLength);
        RequestController.setLocation(destNodeID);
    }

    private void showServiceWindow(final int xcoord, final int ycoord, final int windowWidth, final int windowLength) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/RequestAPI.fxml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
//        RequestController mySC = new RequestController();
//        mySC.set

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
