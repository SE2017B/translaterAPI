package database;

import Node.Node;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class nodeDatabase {

    // Table Schema
    //////////////////////////////////////////////////////////////////
    // nodes (nodeID PK, xCoord, yCoord, floor, building, nodeType,
    //        longName, shortName, teamAssigned)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_MAP="jdbc:derby:hospitalMapDB;create=true";
    private static Connection conn;

    // Counters for # total count on each nodeType
    private static int hallCounter;
    private static int elevCounter;
    private static int restCounter;
    private static int staiCounter;
    private static int deptCounter;
    private static int labsCounter;
    private static int infoCounter;
    private static int confCounter;
    private static int exitCounter;
    private static int retlCounter;
    private static int servCounter;

    // All nodes from the node table in hospitalMapDB
    static ArrayList<Node> allNodes=new ArrayList<>();

    // Getter for Arraylist of all nodes
    public static ArrayList<Node> getNodes(){
        return allNodes;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete nodes table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteNodeTable() {

        try {

            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "NODES", null);

            Statement stmtDelete1 = conn.createStatement();
            String deleteNodesTable = ("DROP TABLE nodes");

            if (res.next()) {
                int rsetDelete1 = stmtDelete1.executeUpdate(deleteNodesTable);
                System.out.println("Drop Node Table Successful!");
                conn.commit();
                stmtDelete1.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the nodes
    ///////////////////////////////////////////////////////////////////////////////
    public static void createNodeTable() {

        System.out.println();

        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "NODES", null);

            //Add a new node table
            Statement stmtCreate1 = conn.createStatement();
            String createNodesTable = ("CREATE TABLE nodes" +
                    "(nodeID VARCHAR(10)," +
                    "xCoord VARCHAR(5)," +
                    "yCoord VARCHAR(5)," +
                    "floor VARCHAR(2)," +
                    "building VARCHAR(10)," +
                    "nodeType VARCHAR(4)," +
                    "longName VARCHAR(75)," +
                    "shortName VARCHAR(50)," +
                    "teamAssigned VARCHAR(6)," +
                    "CONSTRAINT nodes_PK PRIMARY KEY (nodeID)," +
                    "CONSTRAINT floor_chk CHECK (floor IN ('L1', 'L2', 'G', '1', '2', '3'))," +
                    "CONSTRAINT building_chk CHECK (building IN ('BTM', 'Shapiro', 'Tower', '45 Francis', '15 Francis'))," +
                    "CONSTRAINT nodeType_chk CHECK (nodeType IN ('HALL', 'ELEV', 'REST', 'STAI', 'DEPT', 'LABS', 'INFO', 'CONF', 'EXIT', 'RETL', 'SERV'))," +
                    "CONSTRAINT team_chk CHECK (teamAssigned IN ('Team A', 'Team B', 'Team C', 'Team D', 'Team E', 'Team F', 'Team G', 'Team H', 'Team I')))");

            int rsetCreate1 = stmtCreate1.executeUpdate(createNodesTable);
            System.out.println("Create Nodes table Successful!");

            conn.commit();
            System.out.println();

            stmtCreate1.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    ///////////////////////////////////////////////////////////////////////////////
    // Query all nodes from the node table
    ///////////////////////////////////////////////////////////////////////////////
    public static Node findANode(String anyNodeID) {
        Node resultNode = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectANode = conn.createStatement();
            String aNode = "SELECT * FROM NODES WHERE nodeID = " + anyNodeID;
            ResultSet rsetANode = selectANode.executeQuery(aNode);

            String strNodeID;
            String strXCoord;
            String strYCoord;
            String strFloor;
            String strBuilding;
            String strNodeType;
            String strLongName;
            String strShortName;
            String strTeamAssigned;

            //Process the results
            while (rsetANode.next()) {
                strNodeID = rsetANode.getString("nodeID");
                strXCoord = rsetANode.getString("xcoord");
                strYCoord = rsetANode.getString("ycoord");
                strFloor = rsetANode.getString("floor");
                strBuilding = rsetANode.getString("building");
                strNodeType = rsetANode.getString("nodeType");
                strLongName = rsetANode.getString("longName");
                strShortName = rsetANode.getString("shortName");
                strTeamAssigned = rsetANode.getString("teamAssigned");

                resultNode = new Node(strNodeID, strXCoord, strYCoord, strFloor, strBuilding, strNodeType, strLongName, strShortName, strTeamAssigned);
                System.out.printf("%-20s %-20d %-20d %-20s %-20s %-20s %-50s %-30s %-20s\n", strNodeID, strXCoord, strYCoord, strFloor, strBuilding, strNodeType, strLongName, strShortName, strTeamAssigned);
            } // End While

            conn.commit();
            System.out.println();

            rsetANode.close();
            selectANode.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultNode;
    }





}
