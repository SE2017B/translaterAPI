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

        launch(args);
    }
}