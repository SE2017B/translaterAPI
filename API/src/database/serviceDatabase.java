package database;

import java.sql.*;

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
                    "CONSTRAINT serviceRequests_PK PRIMARY KEY (requestID)," +
                    "CONSTRAINT serviceRequests_FK1 FOREIGN KEY (locationID) REFERENCES nodes(nodeID)," +
                    "CONSTRAINT serviceRequests_FK2 FOREIGN KEY (staffID) REFERENCES hospitalStaff(staffID))");

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
}
