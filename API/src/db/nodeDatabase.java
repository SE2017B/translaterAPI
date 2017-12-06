package db;

import Node.Node;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class nodeDatabase {

    // Table Schema
    //////////////////////////////////////////////////////////////////
    // nodes (nodeID PK, xCoord, yCoord, floor, building, nodeType,
    //        longName, shortName, teamAssigned)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_API = "jdbc:derby:hospitalAPIDB;create=true";
    private static Connection conn;

    // All nodes from the node table in hospitalMapDB
    public static ArrayList<Node> allNodes = new ArrayList<>();

    ///////////////////////////////////////////////////////////////////////////////
    // Delete nodes table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteNodeTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
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
            conn = DriverManager.getConnection(JDBC_URL_API);
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
            stmtCreate1.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Read from Staff CSV File and store columns in staff array lists
    ///////////////////////////////////////////////////////////////////////////////
    public static void readNodeCSV(String fname) {
        int count = 0;
        InputStream in = Class.class.getResourceAsStream(fname);
        if(in == null){
            System.out.println("\n\n\nNode help\n\n\n");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] nodeValues = line.split(",");

                if (count != 0) {
                    nodeDatabase.allNodes.add(new Node(nodeValues[0], nodeValues[1], nodeValues[2], nodeValues[3], nodeValues[4], nodeValues[5], nodeValues[6], nodeValues[7], nodeValues[8]));
                    System.out.println("Read Success!");
                }
                count++;
            }
            reader.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Insert into nodes table using a prepared statement from csv
    ///////////////////////////////////////////////////////////////////////////////
    public static void insertNodesFromCSV() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "NODES", null);

            PreparedStatement insertNode = conn.prepareStatement("INSERT INTO nodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");


            for (int j = 0; j < allNodes.size(); j++) {

                insertNode.setString(1, allNodes.get(j).getID());
                insertNode.setInt(2, allNodes.get(j).getX());
                insertNode.setInt(3, allNodes.get(j).getY());
                insertNode.setString(4, nodeDatabase.allNodes.get(j).getFloor().getDbMapping());
                insertNode.setString(5, nodeDatabase.allNodes.get(j).getBuilding());
                insertNode.setString(6, nodeDatabase.allNodes.get(j).getType());
                insertNode.setString(7, nodeDatabase.allNodes.get(j).getLongName());
                insertNode.setString(8, nodeDatabase.allNodes.get(j).getShortName());
                insertNode.setString(9, nodeDatabase.allNodes.get(j).getTeam());

                insertNode.executeUpdate();
            }

            conn.commit();
            System.out.println("HH");

            insertNode.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Nodes csv file
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputNodesCSV() {
        String outNodesFileName = "outputNodes.csv";

        try {
            FileWriter fw1 = new FileWriter(outNodesFileName, false);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter pw1 = new PrintWriter(bw1);

            pw1.println("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName,teamAssigned");
            for (int j = 0; j < nodeDatabase.allNodes.size(); j++) {

                pw1.println(nodeDatabase.allNodes.get(j).getID() + "," +
                        nodeDatabase.allNodes.get(j).getX() + "," +
                        nodeDatabase.allNodes.get(j).getY() + "," +
                        nodeDatabase.allNodes.get(j).getFloor().getDbMapping() + "," +
                        nodeDatabase.allNodes.get(j).getBuilding() + "," +
                        nodeDatabase.allNodes.get(j).getType() + "," +
                        nodeDatabase.allNodes.get(j).getLongName() + "," +
                        nodeDatabase.allNodes.get(j).getShortName() + "," +
                        nodeDatabase.allNodes.get(j).getTeam()
                );
            }
            System.out.println();
            pw1.flush();
            pw1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Query all nodes from the node table
    ///////////////////////////////////////////////////////////////////////////////
    public static Node findANode(String anyNodeID) {
        Node resultNode = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            //Statement selectANode = conn.createStatement();
            String aNode = "SELECT * FROM NODES WHERE nodeID = ?";

            PreparedStatement selectANode = conn.prepareStatement(aNode);
            selectANode.setString(1, anyNodeID);

            ResultSet rsetANode = selectANode.executeQuery();

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
