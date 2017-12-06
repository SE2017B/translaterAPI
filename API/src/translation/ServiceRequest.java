/*
 * Software Engineering 3733, Worcester Polytechnic Institute
 * Team H
 * Code produced for Iteration 2
 * Original author(s): Nicholas Fajardo, Meghana Bhatia
 * The following code
 */

package translation;

import Node.Node;

public class ServiceRequest {
    private int requestID;
    private Node location;
    private String time;
    private String date;
    private Staff assignedPersonnel;
    private String inputData;
    private String severity;

    public ServiceRequest(int requestID, Node location, String time, String date, Staff assignedPersonnel, String severity, String comments) {
        this.requestID = requestID;
        this.location = location;
        this.time = time;
        this.date = date;
        this.assignedPersonnel = assignedPersonnel;
        this.severity = severity;

        this.inputData = comments;
        this.assignedPersonnel.addRequest(this);
    }

    //Getters and Setters

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public Node getLocation() {
        return location;
    }

    public void setLocation(Node location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Staff getAssignedPersonnel() {
        return assignedPersonnel;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        String name = String.valueOf(requestID) + " " + location + " " + time + " " + date + " " + severity + " " + inputData;
        System.out.println(name);
        return name;
    }


}