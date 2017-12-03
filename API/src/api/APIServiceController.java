package api;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class APIServiceController {
    private String clientName;
    private String addComm;
    private String dur;

    @FXML
    private JFXButton btncreate;

    @FXML
    private JFXButton btncancel;

    @FXML
    private JFXDatePicker dateMenu;

    @FXML
    private JFXTimePicker timeMenu;

    @FXML
    private ChoiceBox<Staff> staffChoiceBox;

    @FXML
    private ChoiceBox<Node> locationChoiceBox;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private JFXTextField txtNameClient;

    @FXML
    private JFXTextField txtAdditionalComments;

    @FXML
    private MenuButton durationMenu;

    @FXML
    void cancelPressed(ActionEvent event) {

        languageChoiceBox.setItems(FXCollections.observableList(new ArrayList<String>()));
        languageChoiceBox.setValue(null);

        staffChoiceBox.setItems(FXCollections.observableList(new ArrayList<Staff>()));
        staffChoiceBox.setValue(null);

        languageChoiceBox.setItems(FXCollections.observableList(new ArrayList<String>()));
        languageChoiceBox.setValue(null);

        timeMenu.getEditor().clear();
        dateMenu.getEditor().clear();

        txtNameClient.clear();
        txtAdditionalComments.clear();
//        locationChoiceBox.setItems(FXCollections.observableList(
//                map.getNodesBy(n -> !n.getType().equals("HALL"))));

    }

    @FXML
    void durationSelected(ActionEvent e) {
        dur = ((MenuItem) e.getSource()).getText();
        System.out.println(dur);

    }

    @FXML
    void requestCreatePressed(ActionEvent event) {

        clientName = txtNameClient.getText();
        addComm = txtAdditionalComments.getText();
        System.out.println(clientName + " " + addComm);

    }

}
