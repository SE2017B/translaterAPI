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

    //private ScreenController parent;
    //private HospitalMap map;
    private String serviceType;
    private String time;
    private Staff staffMember;
    private String date;
    private String comments;
    private String severity;
    private String nameServiceFile;
    private String nameDept;
    private String nameService;
    private String nameStaff;
    private String selectedAlg;
    private int staffId;
    private static Node location;
    private int requestIDCount;
    private ArrayList<String> deps;
    private ArrayList<String> tasksList;
    //private DepartmentSubsystem depSub;
    //private Service servSelect;
    private ServiceRequest reqServPls;
    //private CurrentServiceController currentServiceController;
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
//        choiceBoxDept.valueProperty().addListener( (v, oldValue, newValue) -> deptSelected(newValue));
//        choiceBoxService.valueProperty().addListener( (v, oldValue, newValue) -> servSelected(newValue));
//        choiceBoxStaff.valueProperty().addListener( (v, oldValue, newValue) -> staffSelected(newValue));
//        depSub = DepartmentSubsystem.getSubsystem();

        //Display selected request on label
        //todo check for label set up -> RESOLVE SERVICE


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
        //resolveServiceListView.setItems(FXCollections.observableList(serviceDatabase.queryAllServices()));

        onShow();
    }

    public void onShow() {
        // todo check population of request list upon start
        //staff choice box set up


        staffChoiceBox.getItems().addAll(staffForCB);


        //todo when tasks made finish this
        //taskChoiceBox set up
        taskChoiceBox.getItems().addAll(tasksList);
//        System.out.println(depSub.getCurrentLoggedIn().getAllRequest());
//        if(depSub.getCurrentLoggedIn().getAllRequest().isEmpty())
//        {
//            resolveServiceListView.getItems().clear();
//        }else{
//            resolveServiceListView.getItems().addAll(depSub.getCurrentLoggedIn().getAllRequest());
//        }

        //todo populate choice boxes upon start
    }

    @FXML

    public void resolveServicePressed(ActionEvent e) {

        //todo TEST
        serviceDatabase.deleteService(resolveServiceListView.getSelectionModel().getSelectedItem());
        resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());

        System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");

        lblSelectedService.setText("Request");
        lblSelectedAdditionalInfo.setText("Location");
        lblSelectedDT.setText("Date & Time");
        lblSelectedLocation.setText("Additional Info");

    }

    public static void setLocation(String locID) {
        Node loc = nodeDatabase.findANode(locID);
        location = loc;
        if (location == null) {
            System.out.println("double fuck me");
        }
    }

    public void displayFields(Staff newStaff) {
        //todo show on list based on the satff member

        resolveServiceListView.setItems(FXCollections.observableList(serviceDatabase.findStaffMemRequests(newStaff)));
    }

    @FXML
    public void requestCreatePressed(ActionEvent e) {
        requestIDCount++;
        staffMember = staffChoiceBox.getSelectionModel().getSelectedItem();
        //time = ((JFXTimePicker) e.getSource()).getValue().toString();
        //date = ((JFXDatePicker) e.getSource()).getValue().toString();
        comments = txtAreaComments.getText();
        String task = taskChoiceBox.getSelectionModel().getSelectedItem();
        ServiceRequest serv;
        serv = new ServiceRequest(requestIDCount, location, time, date, staffMember, task, severity, comments);
        serviceDatabase.addService(serv);
       // resolveServiceListView.setItems(FXCollections.observableList(serviceDatabase.queryAllServices()));

        System.out.println("request submitted");
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
        System.out.println("Time Selected");
        time = ((JFXTimePicker) e.getSource()).getValue().toString();
    }

    public void dateSelected(ActionEvent e) {
        System.out.println("Date Selected");
        date = ((JFXDatePicker) e.getSource()).getValue().toString();
    }

    @FXML
    void createStaffPressed(ActionEvent event) {

        //todo ADJUST FOR API
        staffId = staffDatabase.getStaffCounter() + 1;

        ArrayList<Staff> tempAL = new ArrayList<>();
        tempAL.addAll(staffForCB);

        System.out.println(usernameTxt.getText() + " " + passwordTxt.getText() + " " + fullNametxt.getText());
        //Service tempService = addStaffServiceChoiceBox.getValue();
        Staff nStaff;
        //todo how to make new staff and send it out
        nStaff = new Staff(usernameTxt.getText(), passwordTxt.getText(), "Sanitation", fullNametxt.getText(), Integer.toString(staffId));
        tempAL.add(nStaff);
        staffDatabase.addStaff(nStaff);
        staffChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(tempAL));
       // staffResolveServiceChoiceBox.getItems().clear();
        staffListView1.getItems().clear();
        staffListView.getItems().clear();
        //staffListView.setItems(FXCollections.observableList(staffForCB));
       // staffChoiceBox.getItems().clear();

        usernameTxt.clear();
        passwordTxt.clear();
        fullNametxt.clear();

        staffForCB.clear();
        staffForCB.addAll(tempAL);

        staffListView1.setItems(FXCollections.observableList(tempAL));
        staffListView.setItems(FXCollections.observableList(tempAL));
        //staffListView.setItems(FXCollections.observableList(staffForCB));

//
//
//        staffDatabase.incStaffCounter();
//        depSub.addStaff(tempService, tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter());
//
//        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }

    @FXML
    void removeStaffPressed(ActionEvent event) {

        ArrayList<Staff> tempAL = new ArrayList<>();
        tempAL.addAll(staffForCB);
        //todo ADJUST FOR API
        if (tempAL.remove(staffListView.getSelectionModel().getSelectedItem())) {
            System.out.println("cant find the node");
        }

        staffDatabase.deleteStaff(staffListView.getSelectionModel().getSelectedItem());

        staffResolveServiceChoiceBox.getItems().clear();
        staffListView1.getItems().clear();
        staffListView.getItems().clear();
        //staffListView.setItems(FXCollections.observableList(staffForCB));
        staffChoiceBox.getItems().clear();

        staffForCB.clear();

        //staffListView.setItems(FXCollections.observableList(staffForCB));
        staffListView.setItems(FXCollections.observableList(tempAL));
        staffListView1.setItems(FXCollections.observableList(tempAL));
        staffChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(tempAL));
        if (staffForCB.isEmpty()) {
            System.out.println("life is good");
            staffForCB.addAll(tempAL);
        }

        removeUsername.setText("Username");
        removeFullName.setText("Full Name");
//        depSub.deleteStaff(tempService, tempUsername);
//
//        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }


    @FXML
    void cancelStaffPressed(ActionEvent event) {
        //todo ADJUST FOR API

        //event.getSource()
    }

    @FXML
    void severitySelected(ActionEvent e) {
        //todo ADJUST FOR API
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
        //staffListView.setItems(FXCollections.observableList(staffForCB));
        staffChoiceBox.getItems().clear();

        staffForCB.clear();
        if (staffForCB.isEmpty() || staffForCB == null) {
            System.out.println("Error");
        }
        staffListView.setItems(FXCollections.observableList(tempAL));
        staffListView1.setItems(FXCollections.observableList(tempAL));
        staffChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(tempAL));
        staffForCB.addAll(tempAL);
    }

    @FXML
    void logoutPressed(ActionEvent event) {
        //todo exit from API
        ((Stage) cancelStaffButton.getScene().getWindow()).close();

    }


}
