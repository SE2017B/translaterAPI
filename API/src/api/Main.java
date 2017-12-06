package api;

import database.nodeDatabase;
import database.serviceDatabase;
import database.staffDatabase;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SampleService.newInstance(primaryStage).run(100, 100, 500, 500, null, "DHALL02802", null);
    }


    public static void main(String[] args) {

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

        new staffDatabase().readStaffCSV("/csv/StaffMembers.csv");
        staffDatabase.insertStaffFromCSV();

        launch(args);

        nodeDatabase.outputNodesCSV();
        staffDatabase.outputStaffCSV();
    }
}
