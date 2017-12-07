package db;

import translation.Staff;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class staffDatabase {

    // Table Schema
    //////////////////////////////////////////////////////////////////
    // hospitalStaff (username U1, password, jobType, fullName, ID PK)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_API = "jdbc:derby:hospitalAPIDB;create=true";
    private static Connection conn;

    // Staff Primary Key Counter
    private static int staffCounter;

    public static int getStaffCounter() {
        int tempCount = staffCounter;
        staffCounter++;
        return tempCount;
    }

    // All staff members from the staff table in hospitalStaffDB
    public static ArrayList<Staff> allStaff = new ArrayList<>();

    ///////////////////////////////////////////////////////////////////////////////
    // Delete staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteStaffTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "HOSPITALSTAFF", null);

            Statement stmtDelete3 = conn.createStatement();
            String deleteStaffTable = ("DROP TABLE HOSPITALSTAFF");

            if (res.next()) {
                int rsetDelete1 = stmtDelete3.executeUpdate(deleteStaffTable);
                System.out.println("Drop Staff Table Successful!");
                conn.commit();
                stmtDelete3.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the Staff Members
    ///////////////////////////////////////////////////////////////////////////////
    public static void createStaffTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "HOSPITALSTAFF", null);

            //Add a new node table
            Statement stmtCreateStaffTable = conn.createStatement();
            String createStaffTable = ("CREATE TABLE hospitalStaff" +
                    "(username VARCHAR(20)," +
                    "password VARCHAR(64)," +
                    "jobTitle VARCHAR(50)," +
                    "fullname VARCHAR(30)," +
                    "ID INTEGER," +
                    "CONSTRAINT hospitalStaff_PK PRIMARY KEY (ID)," +
                    "CONSTRAINT jobTitle CHECK (jobTitle IN ('Translator', 'Janitor', 'Chef', 'Food Delivery', 'Transport Staff', 'Sanitation'))," +
                    "CONSTRAINT ID_chk CHECK (ID > 0))");

            int rsetCreate3 = stmtCreateStaffTable.executeUpdate(createStaffTable);
            System.out.println("Create Staff table Successful!");

            conn.commit();
            stmtCreateStaffTable.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Insert into staff table using a prepared statement from csv
    ///////////////////////////////////////////////////////////////////////////////
    public static void insertStaffFromCSV() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?)");

            for (int j = 0; j < allStaff.size(); j++) {

                insertStaff.setString(1, allStaff.get(j).getUsername());
                insertStaff.setString(2, allStaff.get(j).getPassword());
                insertStaff.setString(3, allStaff.get(j).getJobTitle());
                insertStaff.setString(4, allStaff.get(j).getFullName());
                insertStaff.setString(5, allStaff.get(j).getID());
                getStaffCounter();
                insertStaff.executeUpdate();
            }


            conn.commit();

            insertStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Add a Staff member to Staff table Function
    ///////////////////////////////////////////////////////////////////////////////
    public static void addStaff(Staff anyStaff) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?)");

            addAnyStaff.setString(1, anyStaff.getUsername());
            addAnyStaff.setString(2, anyStaff.getPassword());
            addAnyStaff.setString(3, anyStaff.getJobTitle());
            addAnyStaff.setString(4, anyStaff.getFullName());
            addAnyStaff.setString(5, anyStaff.getID());

            addAnyStaff.executeUpdate();

            conn.commit();

            allStaff.add(new Staff(anyStaff.getUsername(), anyStaff.getPassword(), anyStaff.getJobTitle(), anyStaff.getFullName(), anyStaff.getID()));

            addAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify a Staff Member in the Staff Database
    ///////////////////////////////////////////////////////////////////////////////
    public static void modifyStaff(Staff anyStaff) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String strModDrop = "DELETE FROM hospitalStaff WHERE ID = ?";
            String strModAdd = "INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?)";

            PreparedStatement modDelAnyStaff = conn.prepareStatement(strModDrop);
            modDelAnyStaff.setString(1, anyStaff.getID());
            modDelAnyStaff.executeUpdate();

            PreparedStatement modAddAnyStaff = conn.prepareStatement(strModAdd);

            modAddAnyStaff.setString(1, anyStaff.getUsername());
            modAddAnyStaff.setString(2, anyStaff.getPassword());
            modAddAnyStaff.setString(3, anyStaff.getJobTitle());
            modAddAnyStaff.setString(4, anyStaff.getFullName());
            modAddAnyStaff.setString(5, anyStaff.getID());

            conn.commit();

            modAddAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a staff member from the staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteStaff(Staff anyStaff) {

        String anyStaffID = anyStaff.getID();

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyStaff = conn.prepareStatement("DELETE FROM hospitalStaff WHERE ID = ?");

            // set the corresponding param
            deleteAnyStaff.setString(1, anyStaffID);
            // execute the delete statement
            deleteAnyStaff.executeUpdate();

            conn.commit();
            deleteAnyStaff.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int indexOf = allStaff.indexOf(anyStaff);
        allStaff.remove(indexOf);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Get all staff members from the staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static ArrayList<Staff> queryAllStaff() {

        ArrayList<Staff> resultStaff = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllStaff = conn.createStatement();
            String allStaff = "SELECT * FROM hospitalStaff";
            ResultSet rsetAllStaff = selectAllStaff.executeQuery(allStaff);

            String strUsername;
            String strPW;
            String strTitle;
            String strFullname;
            String intStaffID;

            //Process the results
            while (rsetAllStaff.next()) {
                strUsername = rsetAllStaff.getString("username");
                strPW = rsetAllStaff.getString("password");
                strTitle = rsetAllStaff.getString("jobTitle");
                strFullname = rsetAllStaff.getString("fullName");
                intStaffID = rsetAllStaff.getString("ID");

                resultStaff.add(new Staff(strUsername, strPW, strTitle, strFullname, intStaffID));

            } // End While

            conn.commit();
            System.out.println();

            rsetAllStaff.close();
            selectAllStaff.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultStaff;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Find a staff member from the staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static Staff findAStaff(String anyStaffID) {
        Staff resultStaff = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL_API);
            conn.setAutoCommit(false);
            conn.getMetaData();

            //Statement selectANode = conn.createStatement();
            String aStaff = "SELECT * FROM HOSPITALSTAFF WHERE ID = ?";

            PreparedStatement selectAStaff = conn.prepareStatement(aStaff);
            selectAStaff.setString(1, anyStaffID);

            ResultSet rsetAStaff = selectAStaff.executeQuery();

            //Process the results
            String strUsername;
            String strPW;
            String strTitle;
            String strFullname;
            String intStaffID;

            //Process the results
            while (rsetAStaff.next()) {
                strUsername = rsetAStaff.getString("username");
                strPW = rsetAStaff.getString("password");
                strTitle = rsetAStaff.getString("jobTitle");
                strFullname = rsetAStaff.getString("fullName");
                intStaffID = rsetAStaff.getString("ID");

                resultStaff = new Staff(strUsername, strPW, strTitle, strFullname, intStaffID);

            } // End While

            conn.commit();
            System.out.println();

            rsetAStaff.close();
            selectAStaff.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultStaff;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Read from Staff CSV File and store columns in staff array lists
    ///////////////////////////////////////////////////////////////////////////////
    public static void readStaffCSV(String fname) {
        int count = 0;
        InputStream in = Class.class.getResourceAsStream(fname);
        if(in == null){
            System.out.println("Error");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] staffValues = line.split(",");

                if (count != 0) {
                    staffDatabase.allStaff.add(new Staff(staffValues[0], staffValues[1], staffValues[2], staffValues[3], staffValues[4]));
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
    // Write to a output Staff csv file (No Password Encryption)
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputStaffCSV() {
        String outStaffFileName = "outputStaff.csv";

        try {
            FileWriter fw3 = new FileWriter(outStaffFileName, false);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            PrintWriter pw3 = new PrintWriter(bw3);

            pw3.println("username,password,jobTitle,fullName,ID");
            for (int j = 0; j < staffDatabase.allStaff.size(); j++) {

                pw3.println(staffDatabase.allStaff.get(j).getUsername() + "," +
                        staffDatabase.allStaff.get(j).getPassword() + "," +
                        staffDatabase.allStaff.get(j).getJobTitle() + "," +
                        staffDatabase.allStaff.get(j).getFullName() + "," +
                        staffDatabase.allStaff.get(j).getID()
                );
            }
            System.out.println();
            pw3.flush();
            pw3.close();
            bw3.close();
            fw3.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

