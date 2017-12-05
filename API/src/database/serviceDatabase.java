package database;

import translation.Staff;
import translation.ServiceRequest;
import Node.Node;

import java.sql.*;
import java.util.ArrayList;

public class serviceDatabase {

    // Table Schema
    //////////////////////////////////////////////////////////////////
    // serviceRequests (nodeID PK, xCoord, yCoord, floor, building, nodeType,
    //        longName, shortName, teamAssigned)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_API="jdbc:derby:hospitalAPIDB;create=true";
    private static Connection conn;

    ///////////////////////////////////////////////////////////////////////////////
    // Delete nodes table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteRequestsTable() {

        try {

            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "serviceRequests", null);

            Statement stmtDelete = conn.createStatement();
            String deleteServiceTable = ("DROP TABLE serviceRequests");

            if (res.next()) {
                int rsetDelete = stmtDelete.executeUpdate(deleteServiceTable);
                System.out.println("Drop ServiceRequest Table Successful!");
                conn.commit();
                stmtDelete.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the nodes
    ///////////////////////////////////////////////////////////////////////////////
    public static void createServiceTable() {

        System.out.println();

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "serviceRequests", null);

            //Add a new node table
            Statement stmtCreate = conn.createStatement();
            String createServiceTable = ("CREATE TABLE serviceRequests" +
                    "(requestID INTEGER," +
                    "locationID VARCHAR(10)," +
                    "time VARCHAR(50)," +
                    "date VARCHAR(50)," +
                    "staffID VARCHAR(50)," +
                    "severity VARCHAR(4)," +
                    "comments VARCHAR(75)," +
                    "CONSTRAINT serviceRequests_PK PRIMARY KEY (requestID))");

            int rsetCreate1 = stmtCreate.executeUpdate(createServiceTable);
            System.out.println("Create serviceRequests table Successful!");

            conn.commit();
            System.out.println();

            stmtCreate.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Add a Service
    ///////////////////////////////////////////////////////////////////////////////
    public static void addService(ServiceRequest anyService) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyService = conn.prepareStatement("INSERT INTO serviceRequests VALUES (?, ?, ?, ?, ?, ?, ?)");

            addAnyService.setInt(1, anyService.getRequestID());
            addAnyService.setString(2, anyService.getLocation().getID());
            addAnyService.setString(3, anyService.getTime());
            addAnyService.setString(4, anyService.getDate());
            addAnyService.setInt(5, anyService.getAssignedPersonnel().getID());
            addAnyService.setString(6, anyService.getSeverity());
            addAnyService.setString(7, anyService.getInputData());

            addAnyService.executeUpdate();

            //System.out.printf("Insert Staff Successful for staffID: %-5d\n", anyService.getRequestID());
            //System.out.println();

            conn.commit();

            addAnyService.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a staff member from the staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteService(ServiceRequest anyRequest){

        int anyServiceID = anyRequest.getRequestID();

        try  {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyService = conn.prepareStatement("DELETE FROM serviceRequests WHERE requestID = ?");

            // set the corresponding param
            deleteAnyService.setInt(1, anyServiceID);
            // execute the delete statement
            deleteAnyService.executeUpdate();

            System.out.println("Delete Service Request Successful!");

            conn.commit();
            deleteAnyService.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Get all service requests from the serviceRequest table
    ///////////////////////////////////////////////////////////////////////////////
    public static ArrayList<ServiceRequest> queryAllServices() {

        ArrayList<ServiceRequest> resultServices = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllServices = conn.createStatement();
            String allServices = "SELECT * FROM serviceRequests";
            ResultSet rsetAllServices = selectAllServices.executeQuery(allServices);

            Integer intServiceID;
            String strLocID;
            String strTime;
            String strDate;
            String strStaffID;
            String strSeverity;
            String strComments;

            //Process the results
            while (rsetAllServices.next()) {
                intServiceID = rsetAllServices.getInt("requestID");
                strLocID = rsetAllServices.getString("locationID");
                strTime = rsetAllServices.getString("time");
                strDate = rsetAllServices.getString("date");
                strStaffID = rsetAllServices.getString("staffID");
                strSeverity = rsetAllServices.getString("severity");
                strComments = rsetAllServices.getString("comments");

                Node someNode = nodeDatabase.findANode(strLocID);
                Staff someStaff = staffDatabase.findAStaff(strStaffID);

                resultServices.add(new ServiceRequest(intServiceID, someNode, strTime, strDate, someStaff, strSeverity, strComments));

            } // End While

            conn.commit();
            System.out.println();

            rsetAllServices.close();
            selectAllServices.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultServices;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Get all service requests for a given staff member
    ///////////////////////////////////////////////////////////////////////////////

    public static ArrayList<ServiceRequest> findStaffMemRequests(Staff anyStaff) {

        ArrayList<ServiceRequest> resultServices = new ArrayList<>();
        String anyUser = anyStaff.getUsername();

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String staffMemServices = "SELECT * FROM serviceRequests WHERE username = ?";

            PreparedStatement selectStaffServices = conn.prepareStatement(staffMemServices);
            selectStaffServices.setString(1, anyUser);

            ResultSet rsetStaffServices = selectStaffServices.executeQuery();

            Integer intServiceID;
            String strLocID;
            String strTime;
            String strDate;
            String strStaffID;
            String strSeverity;
            String strComments;

            //Process the results
            while (rsetStaffServices.next()) {

                intServiceID = rsetStaffServices.getInt("requestID");
                strLocID = rsetStaffServices.getString("locationID");
                strTime = rsetStaffServices.getString("time");
                strDate = rsetStaffServices.getString("date");
                strStaffID = rsetStaffServices.getString("staffID");
                strSeverity = rsetStaffServices.getString("severity");
                strComments = rsetStaffServices.getString("comments");

                Staff someStaff = staffDatabase.findAStaff(strStaffID);
                Node someNode = nodeDatabase.findANode(strLocID);
                resultServices.add(new ServiceRequest(intServiceID, someNode, strTime, strDate, someStaff, strSeverity, strComments));

            } // End While

            conn.commit();
            System.out.println();

            rsetStaffServices.close();
            selectStaffServices.close();
            conn.close();
        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultServices;
    }
}
