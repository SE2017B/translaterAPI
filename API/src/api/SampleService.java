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
import java.sql.DriverManager;
import java.sql.SQLException;

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
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        nodeDatabase.deleteNodeTable();
        staffDatabase.deleteStaffTable();
        serviceDatabase.deleteRequestsTable();

        nodeDatabase.createNodeTable();
        staffDatabase.createStaffTable();
        serviceDatabase.createServiceTable();

        nodeDatabase.readNodeCSV("/csv/MapAnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapBnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapCnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapDnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapEnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapFnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapGnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapHnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapInodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapWnodes.csv");
        nodeDatabase.insertNodesFromCSV();

        staffDatabase.readStaffCSV("/csv/staffMembers.csv");
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
