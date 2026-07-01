package oopassignment;
public class FacultyAdmin extends User{
    private String adminID;
    private String officeLocation;
    
    // Constructors
    public FacultyAdmin(String adminID, String name, String password, String email, String gender, String dob, String phoneNum, String officeLocation, String status){
        super(email, password, name, "Faculty Admin", gender, dob, phoneNum, status);
        this.adminID = adminID;
        this.officeLocation = officeLocation;
    }
    
    // Getters
    public String getadminID(){
        return adminID;
    }
    
    
    public String getofficeLocation(){
        return officeLocation;
    }

    
    // Setters
    public void setadminID(String adminID){
        this.adminID = adminID;
    }
    
    
    public void setofficeLocation(String officeLocation){
        this.officeLocation = officeLocation;
    }

}
