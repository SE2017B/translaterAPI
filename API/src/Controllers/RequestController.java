/*
 * Software Engineering 3733, Worcester Polytechnic Institute
 * Team H
 * Code produced for Iteration1
 * Original author(s): Travis Norris, Andrey Yuzvik
 * The following code
 */
package Controllers;

import Node.Node;
import com.jfoenix.controls.*;
import database.nodeDatabase;
import database.serviceDatabase;
import database.staffDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import translation.ServiceRequest;
import translation.Staff;

import java.util.ArrayList;

public class RequestController {

    private String time;
    private Staff staffMember;
    private String date;
    private String comments;
    private String severity;
    private int staffId;
    private static Node location;
    private int requestIDCount;
    private ArrayList<String> tasksList;
    private ArrayList<Staff> staffForCB;

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
    private ChoiceBox<String> taskChoiceBox;

    @FXML
    private JFXTextField txtNameClient;

    @FXML
    private JFXTextArea txtAreaComments;

    @FXML
    private MenuButton durationMenu;

    @FXML
    private Label lblSelectedService;

    @FXML
    private JFXButton btncreate1;

    @FXML
    private Label lblSelectedLocation;

    @FXML
    private Label lblSelectedDT;

    @FXML
    private Label lblSelectedAdditionalInfo;

    @FXML
    private JFXListView<ServiceRequest> resolveServiceListView;

    @FXML
    private Tab staffManagementTab;

    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXTextField passwordTxt;

    @FXML
    private JFXTextField fullNametxt;

    @FXML
    private ChoiceBox<Staff> staffResolveServiceChoiceBox;

    @FXML
    private JFXButton createStaffButton;

    @FXML
    private JFXButton cancelStaffButton;

    @FXML
    private MenuButton severityMenu;

    @FXML
    private MenuItem lowseverityMenu;

    @FXML
    private MenuItem mildseverityMenu;

    @FXML
    private MenuItem extremeseverityMenu;

    @FXML
    private JFXTextField usernameDeleteTxt;

    @FXML
    private JFXButton removeStaffBtn;

    @FXML
    private JFXTextField textFullName;

    @FXML
    private ListView<Staff> staffListView;

    @FXML
    private JFXTextField usernameEdit;

    @FXML
    private JFXButton editStaff;

    @FXML
    private JFXTextField fullnameEdit;

    @FXML
    private JFXTextField passwordEdit;

    @FXML
    private ListView<Staff> staffListView1;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnEditMap;
    @FXML
    private Label removeUsername;

    @FXML
    private Label removeFullName;

    @FXML
    public void initialize() {
        staffForCB = new ArrayList<Staff>();
        staffForCB.addAll(staffDatabase.queryAllStaff());
        tasksList = new ArrayList<String>();
        tasksList.add("Clean Room");
        tasksList.add("Room Prep");
        tasksList.add("Clean Up Hazardous Waste");
        tasksList.add("Clean Up Non-Hazardous Waste");
        tasksList.add("Repair");
        tasksList.add("Other");

        //Display selected request on label
        staffResolveServiceChoiceBox.valueProperty().addListener((v, oldValue, newValue) -> displayFields(newValue));
        staffListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
                                                                                 @Override
                                                                                 public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                                                                                     if (newValue != null) {
                                                                                         removeFullName.setText(newValue.getFullName());
                                                                                         removeUsername.setText(newValue.getUsername());
                                                                                     }
                                                                                 }
                                                                             }
        );

        staffListView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
                                                                                  @Override
                                                                                  public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                                                                                      if (newValue != null) {
                                                                                          fullnameEdit.setText(newValue.getFullName());
                                                                                          usernameEdit.setText(newValue.getUsername());
                                                                                          passwordEdit.setText(newValue.getPassword());
                                                                                      }
                                                                                  }
                                                                              }
        );
        resolveServiceListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ServiceRequest>() {
                                                                                  @Override
                                                                                  public void changed(ObservableValue<? extends ServiceRequest> observable, ServiceRequest oldValue, ServiceRequest newValue) {
                                                                                      if (newValue != null) {
                                                                                          lblSelectedService.setText(newValue.getTask());
                                                                                          lblSelectedLocation.setText(newValue.getLocation().toString());
                                                                                          lblSelectedDT.setText(newValue.getDate() + " | " + newValue.getTime());
                                                                                          lblSelectedAdditionalInfo.setText(newValue.getSeverity());
                                                                                      }
                                                                                  }
                                                                              }
        );
        staffListView.setItems(FXCollections.observableList(staffForCB));
        staffListView1.setItems(FXCollections.observableList(staffForCB));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(staffForCB));
        onShow();
    }

    public void onShow() {

        staffChoiceBox.getItems().addAll(staffForCB);
        taskChoiceBox.getItems().addAll(tasksList);
    }

    @FXML
    public void resolveServicePressed(ActionEvent e) {

        serviceDatabase.deleteService(resolveServiceListView.getSelectionModel().getSelectedItem());
        resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());

        //System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");

        lblSelectedService.setText("Request");
        lblSelectedAdditionalInfo.setText("Location");
        lblSelectedDT.setText("Date & Time");
        lblSelectedLocation.setText("Additional Info");

    }

    public static void setLocation(String locID) {
        Node loc = nodeDatabase.findANode(locID);
        location = loc;
        if (location == null) {
            System.out.println("Error");
        }
    }

    public void displayFields(Staff newStaff) {
        if(newStaff != null) {
            resolveServiceListView.setItems(FXCollections.observableList(serviceDatabase.findStaffMemRequests(newStaff)));
        }
    }

    @FXML
    public void requestCreatePressed(ActionEvent e) {
        requestIDCount++;
        staffMember = staffChoiceBox.getSelectionModel().getSelectedItem();
        comments = txtAreaComments.getText();
        String task = taskChoiceBox.getSelectionModel().getSelectedItem();
        ServiceRequest serv;
        serv = new ServiceRequest(requestIDCount, location, time, date, staffMember, task, severity, comments);
        serviceDatabase.addService(serv);

        // Clear all fields
        staffChoiceBox.setItems(FXCollections.observableList(staffForCB));
        taskChoiceBox.setItems(FXCollections.observableList(tasksList));
        timeMenu.getEditor().clear();
        dateMenu.getEditor().clear();
        txtAreaComments.clear();
        severityMenu.setText("");
    }

    public void cancelPressed(ActionEvent e) {

        staffChoiceBox.setItems(FXCollections.observableList(staffForCB));
        taskChoiceBox.setItems(FXCollections.observableList(tasksList));
        timeMenu.getEditor().clear();
        dateMenu.getEditor().clear();
        txtAreaComments.clear();
        severityMenu.setText("");
    }

    public void timeSelected(ActionEvent e) {
        time = ((JFXTimePicker) e.getSource()).getValue().toString();
    }

    public void dateSelected(ActionEvent e) {
        date = ((JFXDatePicker) e.getSource()).getValue().toString();
    }

    @FXML
    void createStaffPressed(ActionEvent event) {
        staffId = staffDatabase.getStaffCounter() + 1;
        ArrayList<Staff> tempAL = new ArrayList<>();
        tempAL.addAll(staffForCB);
        Staff nStaff;

        nStaff = new Staff(usernameTxt.getText(), passwordTxt.getText(), "Sanitation", fullNametxt.getText(), Integer.toString(staffId));
        tempAL.add(nStaff);
        staffDatabase.addStaff(nStaff);
        staffChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffListView1.getItems().clear();
        staffListView.getItems().clear();
        usernameTxt.clear();
        passwordTxt.clear();
        fullNametxt.clear();
        staffForCB.clear();
        staffForCB.addAll(tempAL);
        staffListView1.setItems(FXCollections.observableList(tempAL));
        staffListView.setItems(FXCollections.observableList(tempAL));
    }

    @FXML
    void removeStaffPressed(ActionEvent event) {
        ArrayList<Staff> tempAL = new ArrayList<>();
        tempAL.addAll(staffForCB);

        if (tempAL.remove(staffListView.getSelectionModel().getSelectedItem())) {
            System.out.println("cant find the node");
        }

        staffDatabase.deleteStaff(staffListView.getSelectionModel().getSelectedItem());

        staffResolveServiceChoiceBox.getItems().clear();
        staffListView1.getItems().clear();
        staffListView.getItems().clear();
        staffChoiceBox.getItems().clear();

        staffForCB.clear();

        staffListView.setItems(FXCollections.observableList(tempAL));
        staffListView1.setItems(FXCollections.observableList(tempAL));
        staffChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(tempAL));

        if (staffForCB.isEmpty()) {
            staffForCB.addAll(tempAL);
        }

        removeUsername.setText("Username");
        removeFullName.setText("Full Name");
    }


    @FXML
    void cancelStaffPressed(ActionEvent event) {
        //event.getSource()
    }

    @FXML
    void severitySelected(ActionEvent e) {
        severity = ((MenuItem) e.getSource()).getText();
        severityMenu.setText(severity);
    }

    @FXML
    void editStaffPressed(ActionEvent event) {
        ArrayList<Staff> tempAL = new ArrayList<>(staffForCB);

        String tempUsername = usernameEdit.getText();
        String tempPassword = passwordEdit.getText();
        String tempFullName = fullnameEdit.getText();

        usernameEdit.clear();
        passwordEdit.clear();
        fullnameEdit.clear();

        Staff tempStaff = staffListView1.getSelectionModel().getSelectedItem();
        tempStaff.updateCredidentials(tempUsername, tempPassword, "Sanitation", tempFullName);

        staffResolveServiceChoiceBox.getItems().clear();
        staffListView1.getItems().clear();
        staffListView.getItems().clear();
        staffChoiceBox.getItems().clear();

        staffForCB.clear();
        if (staffForCB.isEmpty() || staffForCB == null) {
            System.out.println("Error: No staff members found.");
        }
        staffListView.setItems(FXCollections.observableList(tempAL));
        staffListView1.setItems(FXCollections.observableList(tempAL));
        staffChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffForCB.addAll(tempAL);
    }

    @FXML
    void logoutPressed(ActionEvent event) {
        nodeDatabase.
        ((Stage) cancelStaffButton.getScene().getWindow()).close();
    }
}
