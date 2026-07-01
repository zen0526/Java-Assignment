package oopassignment;

import java.util.ArrayList;

public class Supervisor extends User{
    private String supervisorID;
    private String department;
    private String numberofStudent;
    private String officeLocation;
    
    // Constructors
    public Supervisor(String supervisorID, String name, String password, String email, String gender, String dob, String phoneNum, String department, String numberofStudent, String officeLocation, String status){
        super(email, password, name, "supervisor", gender, dob, phoneNum, status);
        this.supervisorID = supervisorID;
        this.department = department;
        this.numberofStudent = numberofStudent;
        this.officeLocation = officeLocation;
    }
    
    // Getters
    public String getsupervisorID(){
        return supervisorID;
    }
    
    public String getDepartment(){
        return department;
    }
    
    public String getnumberofStudent(){
        return numberofStudent;
    }
    
    public String getofficeLocation(){
        return officeLocation;
    }
    
    
    // Setters
    public void setsupervisorID(String supervisorID){
        this.supervisorID = supervisorID;
    }
    
    public void setDepartment(String department){
        this.department = department;
    }
    
    public void setnumberofStudent(String numberofStudent){
        this.numberofStudent = numberofStudent;
    }
    
    public void setofficeLocation(String officeLocation){
        this.officeLocation = officeLocation;
    }
    
    public String[] getSupervisorDataList(){
        // {"SupervisorID","Name","Department","Num of Students","Office Location","Status"};
        String[] dataLine = {supervisorID,super.getName(),department,numberofStudent,officeLocation,super.getStatus()};
        return dataLine;
    }
    public String[] getSupervisorFileDataList(){
        // {"SupervisorID","Name","password","email","gender","dob","phone num","Departmnet","Num of Students","Office Location","Status"};
        String[] fileDataLine = {supervisorID,super.getName(),super.getPassword(),super.getEmail(),super.getGender(),super.getdob(),super.getphoneNum(),department,numberofStudent,officeLocation,super.getStatus()};
        return fileDataLine;
    }
    
    public void countSupervisorNumberOfStudent(ArrayList<Assignment> assignmentList){
        int count = 0;
        for (Assignment data : assignmentList){
            if (data.getSupervisorID().equals(supervisorID) && data.getAssignStatus().equals("Active")){
                count++;
            }
        }
        this.numberofStudent = String.valueOf(count);
    }    
}
