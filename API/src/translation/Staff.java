/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package translation;


import java.util.*;

public class Staff{
    //Only essential for logging in
    private String username;
    private String password;
    private boolean admin;
    //These are just details for display
    private HashMap<Integer,ServiceRequest> workload;
    private String jobTitle;
    private String fullName;
    private int ID;

    //Constructor DB uses
    public Staff(String username, String password, String jobTitle, String fullName, int ID){
        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;

        //Setting stuff to blanks for now
        workload = new HashMap<Integer, ServiceRequest>();
    }

    public void removeRequest(ServiceRequest request){
        workload.remove(request.getRequestID());
    }

    //Takes in an old password and a new password, and changes the staff's current password to the new one.

    //Update
//    public void uptadeDB(){
//        mainDatabase.modifyStaff(this);
//    }

    //Important Getters and Setters
    public boolean isAdmin() {
        return admin;
    }

    //Other Getters and Setters
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String toString(){
        return fullName;
    }

    public void updateCredidentials(String username, String password, String jobTitle, String fullName, int id) {
        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = id;
    }

    public void addRequest(ServiceRequest newRequest){
        workload.put(newRequest.getRequestID(), newRequest);
    }
    public Collection<ServiceRequest> getAllRequest(){
        return workload.values();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Staff)) return false;
        Staff other = (Staff)obj;
        return this.getUsername().equals(other.getUsername());
    }
}