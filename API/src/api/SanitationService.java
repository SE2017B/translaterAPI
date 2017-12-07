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

        nodeDatabase.deleteNodeTable();
        staffDatabase.deleteStaffTable();
        serviceDatabase.deleteRequestsTable();

        nodeDatabase.createNodeTable();
        staffDatabase.createStaffTable();
        serviceDatabase.createServiceTable();
        //if((nodeDatabase.allNodes == null || nodeDatabase.allNodes.isEmpty())&&(staffDatabase.allStaff == null || staffDatabase.allStaff.isEmpty())) {
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
            nodeDatabase.queryAllNodes();
            nodeDatabase.insertNodesFromCSV();

            staffDatabase.readStaffCSV("/rez/staffMembers.csv");
            staffDatabase.queryStaff();
            staffDatabase.insertStaffFromCSV();
        //}
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

        primaryStage.setTitle("Make New Translation Request");
        primaryStage.setScene(new Scene(root, windowWidth, windowLength));
        primaryStage.setX(xcoord);
        primaryStage.setY(ycoord);
        primaryStage.show();
    }

    public static SanitationService newInstance(final Stage primaryStage) {
        return new SanitationService(primaryStage);
    }

}
