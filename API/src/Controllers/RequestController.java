/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/
package Controllers;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

import javafx.stage.Stage;
import translation.*;
import Node.*;
import api.exceptions.*;
import database.*;

public class RequestController
{

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
    public void initialize()
    {
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


        staffResolveServiceChoiceBox.valueProperty().addListener( (v, oldValue, newValue) -> displayFields());
        staffListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
            @Override
            public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue){
                removeFullName.setText(newValue.getFullName());
                removeUsername.setText(newValue.getUsername());
            }
        }
        );

        staffListView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
            @Override
            public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue){
                fullnameEdit.setText(newValue.getFullName());
                usernameEdit.setText(newValue.getUsername());
                passwordEdit.setText(newValue.getPassword());
            }
        }
        );
        staffListView.setItems(FXCollections.observableList(staffDatabase.queryAllStaff()));
        staffListView1.setItems(FXCollections.observableList(staffDatabase.queryAllStaff()));
        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(staffDatabase.queryAllStaff()));

        onShow();
    }

    public void onShow()
    {
        // todo check population of request list upon start
        //staff choice box set up
        ArrayList<Staff> staffForCB = new ArrayList<Staff>();
        staffForCB.addAll(staffDatabase.queryAllStaff());
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

    public void resolveServicePressed(ActionEvent e)
    {

    //todo TEST
        resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());
        System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");

        lblSelectedService.setText("Request");
        lblSelectedAdditionalInfo.setText("Location");
        lblSelectedDT.setText("Date & Time");
        lblSelectedLocation.setText("Additional Info");

    }

    public static void setLocation(String locID){
        Node loc = nodeDatabase.findANode(locID);
        location = loc;
    }

    public void displayFields()
    {
        //resolveServiceListView
    }

    @FXML
    public void requestCreatePressed(ActionEvent e)
    {
        //todo create the request
        requestIDCount++;


        staffMember = staffChoiceBox.getSelectionModel().getSelectedItem();
        comments = txtAreaComments.getText();
        ServiceRequest serv;
        serv = new ServiceRequest(requestIDCount, location, time, date, staffMember, severity, comments);
       // serviceDatabase.addService(requestIDCount, location, time, date, staffMember, severity, comments);
//
//Submit request
//        //depSub.submitRequest(choiceBoxService.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString() , locationChoiceBox.getValue(), choiceBoxStaff.getValue(),requestIDCount, false, "EMAIL");
//
//        //ServiceRequest nReq = new ServiceRequest(choiceBoxService.getValue(), requestIDCount, locationChoiceBox.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString(), choiceBoxStaff.getValue());
//
        //Add new service to List
        System.out.println("request submitted");

    }

    public void cancelPressed(ActionEvent e)
    {
//        //todo TEST
//        languageChoiceBox.setItems(FXCollections.observableList(new ArrayList<String>()));
//        languageChoiceBox.setValue(null);
//
        //severityMenu = ((MenuItem) e.getSource()).getText().toString();
        timeMenu.getEditor().clear();
        dateMenu.getEditor().clear();
        txtAreaComments.clear();


        System.out.println("cancel pressed");

    }

    public void timeSelected(ActionEvent e)
    {
        System.out.println("Time selescted");
        time = ((JFXTimePicker)e.getSource()).getValue().toString();
    }

    public void dateSelected(ActionEvent e)
    {
        System.out.println("Date Selected" );
        date = ((JFXDatePicker)e.getSource()).getValue().toString();
    }

    @FXML
    void createStaffPressed(ActionEvent event)
    {
//
        //todo ADJUST FOR API
        String tempUsername = usernameTxt.getText();
        String tempPassword = passwordTxt.getText();
        String tempFullName = fullNametxt.getText();
        staffId = staffDatabase.getStaffCounter() + 10;


        System.out.println(usernameTxt.getText() + " " + passwordTxt.getText() + " " + fullNametxt.getText());
        //Service tempService = addStaffServiceChoiceBox.getValue();
        Staff nStaff;
        //todo how to make new staff and send it out
        nStaff = new Staff(usernameTxt.getText(), passwordTxt.getText(),"Sanitation", fullNametxt.getText(), staffId);
        staffDatabase.addStaff(nStaff);
        staffListView.setItems(FXCollections.observableList(staffDatabase.queryAllStaff()));
//
//
//        staffDatabase.incStaffCounter();
//        depSub.addStaff(tempService, tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter());
//
//        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }

    @FXML
    void removeStaffPressed(ActionEvent event)
    {
        //todo ADJUST FOR API
        staffDatabase.deleteStaff(staffListView.getSelectionModel().getSelectedItem());
        staffListView.setItems(FXCollections.observableList(staffDatabase.queryAllStaff()));

//        depSub.deleteStaff(tempService, tempUsername);
//
//        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }


    @FXML
    void cancelStaffPressed(ActionEvent event)
    {
        //todo ADJUST FOR API

       //event.getSource()
    }

    @FXML
    void severitySelected(ActionEvent e)
    {
    //todo ADJUST FOR API
        severity = ((MenuItem) e.getSource()).getText();
        severityMenu.setText(severity);

    }

    @FXML
    void editStaffPressed(ActionEvent event)
    {
    //todo ADJUST FOR API

}

    @FXML
    void logoutPressed(ActionEvent event)
    {
        //todo exit from API
        ((Stage)cancelStaffButton.getScene().getWindow()).close();

    }




}
