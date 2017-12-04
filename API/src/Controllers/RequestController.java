/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import translation.*;
import Node.*;
import exceptions.*;

public class RequestController
{

        //private ScreenController parent;
        //private HospitalMap map;
        private String serviceType;
        private String time;
        private Staff staffMember;
        private String date;
        private String nameServiceFile;
        private String nameDept;
        private String nameService;
        private String nameStaff;
        private String selectedAlg;
        private ArrayList<String> deps;
        private ArrayList<Service> serv;
        private DepartmentSubsystem depSub;
        private Service servSelect;
        private ServiceRequest reqServPls;
        private CurrentServiceController currentServiceController;

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
        private ChoiceBox<String> languageChoiceBox;

        @FXML
        private JFXTextField txtNameClient;

        @FXML
        private JFXTextField txtAdditionalComments;

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
        private JFXButton createStaffButton;

        @FXML
        private JFXButton cancelStaffButton;

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


    public void init()
    {
//        map = HospitalMap.getMap();

//        choiceBoxDept.valueProperty().addListener( (v, oldValue, newValue) -> deptSelected(newValue));
//        choiceBoxService.valueProperty().addListener( (v, oldValue, newValue) -> servSelected(newValue));
//        choiceBoxStaff.valueProperty().addListener( (v, oldValue, newValue) -> staffSelected(newValue));
//        depSub = DepartmentSubsystem.getSubsystem();

        //Display selected request on label
        //todo check for label set up -> RESOLVE SERVICE
//        resolveServiceListView.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<ServiceRequest>() {
//                    @Override
//                    public void changed(ObservableValue<? extends ServiceRequest> observable,
//                                        ServiceRequest oldValue, ServiceRequest newValue) {
//
//                        lblSelectedService.setText(newValue.getService().toString());
//                        lblSelectedAdditionalInfo.setText(newValue.getInputData());
//                        lblSelectedDT.setText(newValue.getTime());
//                        lblSelectedLocation.setText(newValue.getLocation().toString());
//
//                    }
//                }
//        );
    }

    public void onShow()
    {
        // todo check population of request list upon start
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

    public void requestCreatePressed(ActionEvent e)
    {
        //todo create the request
//        requestIDCount++;
//
//        //fillInServiceSpecificRecs();
//
//        //Submit request
//        //depSub.submitRequest(choiceBoxService.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString() , locationChoiceBox.getValue(), choiceBoxStaff.getValue(),requestIDCount, false, "EMAIL");
//
//        //ServiceRequest nReq = new ServiceRequest(choiceBoxService.getValue(), requestIDCount, locationChoiceBox.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString(), choiceBoxStaff.getValue());
//
//        //Add new service to List
//        System.out.println("request submitted");
//        nReq.setInputData(currentServiceController.getInputData());
//        resolveServiceListView.getItems().add(nReq);
//        //fillInServiceSpecificRecs();

    }

    public void cancelPressed(ActionEvent e)
    {
        //todo TEST
        languageChoiceBox.setItems(FXCollections.observableList(new ArrayList<String>()));
        languageChoiceBox.setValue(null);

        // durationMenu = ((MenuItem) e.getSource()).getText();

        timeMenu.getEditor().clear();
        dateMenu.getEditor().clear();

        txtNameClient.clear();
        txtAdditionalComments.clear();
//        locationChoiceBox.setItems(FXCollections.observableList(
//                map.getNodesBy(n -> !n.getType().equals("HALL"))));

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
//        String tempUsername = usernameTxt.getText();
//        String tempPassword = passwordTxt.getText();
//        String tempJobTitle = jobTitletxt.getText();
//        String tempFullName = fullNametxt.getText();
//        Service tempService = addStaffServiceChoiceBox.getValue();
//
//
//        staffDatabase.incStaffCounter();
//        depSub.addStaff(tempService, tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter());
//
//        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }


    @FXML
    void makeModify(ActionEvent event)
    {
        //todo ADJUST FOR API

    }

    @FXML
    void removeStaffPressed(ActionEvent event)
    {
        //todo ADJUST FOR API
//        String tempUsername = usernameDeleteTxt.getText();
//        Service tempService = staffJobTypeChoiceBox.getValue();
//
//        depSub.deleteStaff(tempService, tempUsername);
//
//        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }

    @FXML
    void searchbuttonPressed(ActionEvent event)
    {
        //todo ADJUST FOR API

    }

    @FXML
    void cancelStaffPressed(ActionEvent event)
    {
        //todo ADJUST FOR API

    }

    @FXML
    void durationSelected(ActionEvent event)
    {

    //todo ADJUST FOR API
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
    }
}