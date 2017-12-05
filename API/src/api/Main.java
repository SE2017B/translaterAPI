package api;

import database.nodeDatabase;
import database.serviceDatabase;
import database.staffDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SampleService.newInstance(primaryStage).run(100, 100, 500, 500, null, null, null);
    }


    public static void main(String[] args) {

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        serviceDatabase.deleteRequestsTable();
        nodeDatabase.deleteNodeTable();
        staffDatabase.deleteStaffTable();

        nodeDatabase.createNodeTable();
        staffDatabase.createStaffTable();
//        serviceDatabase.createServiceTable();

        nodeDatabase.readNodeCSV("API/src/csv/MapAnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapBnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapCnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapDnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapEnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapFnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapGnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapHnodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapInodes.csv");
        nodeDatabase.readNodeCSV("API/src/csv/MapWnodes.csv");
        nodeDatabase.insertNodesFromCSV();

        staffDatabase.readStaffCSV("API/src/csv/staffMembers.csv");
        staffDatabase.insertStaffFromCSV();

        nodeDatabase.findANode("IREST00203");
        staffDatabase.queryAllStaff();

        launch(args);

        nodeDatabase.outputNodesCSV();
        staffDatabase.outputStaffCSV();
    }
}
