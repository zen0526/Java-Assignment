package oopassignment;
public class SystemAdmin extends User{
    private String adminID;
    
    // Constructor
    public SystemAdmin(String adminID, String name, String password, String email, String gender, String dob, String phoneNum, String status){
        super(email, password, name, "System admin", gender, dob, phoneNum, status);
        this.adminID = adminID;
    }
    
    // Getters
    public String getadminID(){
        return adminID;
    }
    
    //Setters
    public void setadminID(String adminID){
        this.adminID = adminID;
    }
}
