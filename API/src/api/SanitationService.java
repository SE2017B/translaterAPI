package api;

import Rollers.RequestController;
import api.exceptions.ServiceException;
import db.nodeDatabase;
import db.serviceDatabase;
import db.staffDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SanitationService implements ExportableServiceComponent {
    private final Stage primaryStage;

    private SanitationService(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String destNodeID, String originNodeID) throws ServiceException {
        SanitationServicePreconditions.verifyStartingCoordinates(xcoord, ycoord);
        SanitationServicePreconditions.verifyWindowDimensions(windowLength, windowWidth);
        // TODO: Run other precondition checks here
        // All checks passed; spawn the window
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n\n\nhelp me\n\n\n\n");


        nodeDatabase.deleteNodeTable();
        staffDatabase.deleteStaffTable();
        serviceDatabase.deleteRequestsTable();

        nodeDatabase.createNodeTable();
        staffDatabase.createStaffTable();
        serviceDatabase.createServiceTable();

        nodeDatabase.readNodeCSV("/rez/MapAnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapBnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapCnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapDnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapEnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapFnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapGnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapHnodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapInodes.csv");
        nodeDatabase.readNodeCSV("/rez/MapWnodes.csv");
        nodeDatabase.insertNodesFromCSV();

        staffDatabase.readStaffCSV("/rez/staffMembers.csv");
        staffDatabase.insertStaffFromCSV();

        System.out.println("\n\n\n\nhelp me\n\n\n\n");

//        nodeDatabase.outputNodesCSV();
//        staffDatabase.outputStaffCSV();
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

    public static SanitationService newInstance(final Stage primaryStage) {
        return new SanitationService(primaryStage);
    }

}
