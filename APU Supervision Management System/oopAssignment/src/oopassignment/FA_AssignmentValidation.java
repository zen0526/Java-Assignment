/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopassignment;


import oopassignment.Assignment;
import java.util.ArrayList;

public class FA_AssignmentValidation {
    
    ArrayList<Assignment> assignmentList;
    ArrayList<Student> studentList;
    ArrayList<Supervisor> supervisorList;
    
    //edit Flag to check if user is Assigning new value or Editing assign value
    private boolean editFlag = false;
    private String assignIDBeforeEdit = "";

    
    
    //input values
    String assignID = "";
    String tpNum = "";
    String supervisorID = "";
    String studentCourse = "";
    
    
    //derived values
    
    String supervisorDepartment = "";
    String studentStatus = "";
    String supervisorStatus = "";
    
    private String supervisorName = "";
    private String studentName = "";
    private String studentIntake = "";
    
    private String assignmentStatus = "";
    
    
    //Entering user input into Contructor
    FA_AssignmentValidation(String assignID, String tpNum, String supervisorID, String studentCourse, ArrayList<Student> studentList, ArrayList<Supervisor> supervisorList, ArrayList<Assignment> assignmentList){
        this.assignID = assignID;
        this.tpNum = tpNum;
        this.supervisorID = supervisorID;
        this.studentCourse = studentCourse.toLowerCase();
        this.studentList = studentList;
        this.supervisorList = supervisorList;
        this.assignmentList = assignmentList;
    }

    
    //Ensure that no Text Field is empty
    boolean ensureNoEmptyInput(){
        boolean noEmptyInput = false;
        String[] inputValues = {assignID,tpNum,supervisorID,studentCourse};
        for (String value : inputValues){
            if (value.equals("")){
                return noEmptyInput;
            }
        }
        noEmptyInput = true;
        return noEmptyInput;
    }
    
    //Ensure that Input values actually exists in the system
    boolean ensureInputExists(){
        boolean inputExists;
        
        boolean validStudent = false;
        boolean validSupervisor = false;
        
        for (Student student : studentList){
            
            if ((student.gettpNum().equals(tpNum)) && ((student.getCourse().toLowerCase().equals(studentCourse)))){
                studentCourse = student.getCourse();
                validStudent = true;
                break;
            }

        }
        
        for (Supervisor supervisor : supervisorList){
            if (supervisor.getsupervisorID().equals(supervisorID)){
                supervisorDepartment = supervisor.getDepartment();
                validSupervisor = true;
                break;
            }
        }

        inputExists = (validStudent && validSupervisor);
        return inputExists;
    }
    
    //Ensure that Student and Supervisor comes from the same Department/Course
    boolean ensureStudentSupervisorSameDepartment(){
        boolean sameDepartment = false;

        if ((studentCourse.toLowerCase()).equals(supervisorDepartment.toLowerCase())){
            sameDepartment = true;
        }
        return sameDepartment;  
    }
    
    //Ensure that both student and supervisor are Active
    boolean ensureStudentSupervisorActive(){
        boolean bothActive = false;
        
        for (Student student : studentList){
        
            if (student.gettpNum().equals(tpNum)){
                studentStatus = student.getStatus();
                break;
            }
        }
        for (Supervisor supervisor : supervisorList){
            if (supervisor.getsupervisorID().equals(supervisorID)){
                supervisorStatus = supervisor.getStatus();
                break;
            }
        }
       if (studentStatus.equals("Active") && supervisorStatus.equals("Active")){
            bothActive = true;
        }
        return bothActive;
    }
    
    //Ensure that entered StudentID not: already exists and active in Assignment table
    boolean ensureNoDuplicatedStudent(){
        boolean noDuplicatedStudentFlag = false;
        
        for (int i=0; i<assignmentList.size(); i++){
            if (editFlag == true){
                // entered tpnumber is the same and active
                if (assignmentList.get(i).getAssignStatus().equals("Active") && assignmentList.get(i).getAssignID().equals(assignIDBeforeEdit) && assignmentList.get(i).getTpNum().equals(tpNum)){
                    continue;
                }
            }
            if (assignmentList.get(i).getTpNum().equals(tpNum) && assignmentList.get(i).getAssignStatus().equals("Active")){
                return noDuplicatedStudentFlag;
            }
        }
        noDuplicatedStudentFlag = true;
        return noDuplicatedStudentFlag;
    }
    
    
    
    
    
    //Ensure that there are no Duplicated AssignID
    boolean ensureNoDuplicatedAssignID(){
        boolean noDuplicatedAssignIDFlag = false;
        
        for (int i=0; i<assignmentList.size(); i++){
            if (editFlag == true){
                if (assignmentList.get(i).getAssignID().equals(assignIDBeforeEdit)){
                    continue;
                }
            }
            if (assignmentList.get(i).getAssignID().equals(assignID)){
                return noDuplicatedAssignIDFlag;
            }
        }

        noDuplicatedAssignIDFlag = true;
        return noDuplicatedAssignIDFlag;
    }
    
    //Ensure that AssignID is in format of AXXX
    boolean ensureCorrectAssignIDFormat(){
        return assignID.matches("^A\\d{3}$");
    }
    
    //Set other supervisor & student info for displaying in table
    void deriveSupervisorStudentInfo(){
        
        for (Student student : studentList){
            if (student.gettpNum().equals(tpNum)){
                studentName = student.getName();
                studentIntake = student.getintakecode();
            }
        }
        
        for (Supervisor supervisor : supervisorList){
            if (supervisor.getsupervisorID().equals(supervisorID)){
                supervisorName = supervisor.getName();
            }
        }
    }
    
    
    public String getStudentCourse(){
        return studentCourse;
    }
    
    public String getAssignmentStatus() {
        return assignmentStatus;
    }
    
    public void setAssignmentStatus(String status){
        assignmentStatus = status;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentIntake() {
        return studentIntake;
    }
    
    
    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }
    
    public void setAssignIDBeforeEdit(String assignIDBeforeEdit) {
        this.assignIDBeforeEdit = assignIDBeforeEdit;
    }
    
        
        
    

    
}




