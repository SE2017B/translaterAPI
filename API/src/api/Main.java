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

        serviceDatabase.deleteRequestsTable();
        nodeDatabase.deleteNodeTable();
        staffDatabase.deleteStaffTable();

        nodeDatabase.createNodeTable();
        staffDatabase.createStaffTable();
        serviceDatabase.createServiceTable();

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

        //void sd = new staffDatabase().readStaffCSV("API/src/csv/MapAnodes.csv");

        staffDatabase.insertStaffFromCSV();

        launch(args);

        nodeDatabase.outputNodesCSV();
        staffDatabase.outputStaffCSV();
    }
}
