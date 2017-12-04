package api;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SampleService.newInstance(primaryStage).run(100, 100, 500, 500, null, null, null);
    }


    public static void main(String[] args) {


        launch(args);
    }
}
