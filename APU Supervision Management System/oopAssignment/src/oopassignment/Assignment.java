/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopassignment;

import java.util.ArrayList;


/**
 *
 * @author User
 */
public class Assignment {
    private String assignID;
    private String supervisorID;
    private String supervisorName;
    private String tpNum;
    private String studentName;
    private String intakeCode;
    private String course;
    private String assignStatus;
    
    private String supervisorStatus;
    private String studentStatus;
    
    public Assignment(String assignID, String supervisorID, String supervisorName,String tpNum, String studentName, String intakeCode,String course, String assignStatus) {
        this.assignID = assignID;
        this.supervisorID = supervisorID;
        this.supervisorName = supervisorName;
        this.tpNum = tpNum;
        this.studentName = studentName;
        this.intakeCode = intakeCode;
        this.course = course;
        this.assignStatus = assignStatus;
    }

    public String getAssignID() {
        return assignID;
    }

    public void setAssignID(String assignID) {
        this.assignID = assignID;
    }

    public String getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getTpNum() {
        return tpNum;
    }

    public void setTpNum(String tpNum) {
        this.tpNum = tpNum;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIntakeCode() {
        return intakeCode;
    }

    public void setIntakeCode(String intakeCode) {
        this.intakeCode = intakeCode;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }
    
    // update assign Status to "Inactive" if Student/Supervisor is Inactive
    public String verifyAssignStatus(ArrayList<Supervisor> supervisorList, ArrayList<Student> studentList){
        boolean supervisorFound = false;
        boolean studentFound = false;
        
        for (Supervisor supervisor : supervisorList){
            if (supervisor.getsupervisorID().equals(supervisorID)){
                supervisorStatus = supervisor.getStatus();
                supervisorFound = true;
                break;
            }
        }
        for (Student student : studentList){
            if (student.gettpNum().equals(tpNum)){
                studentStatus = student.getStatus();
                studentFound = true;
                break;
            }
        }
        if (studentFound = false || supervisorFound == false){
            assignStatus = "Inactive";
            return "Inactive";
        }
        else if (studentStatus.equals("Inactive") || supervisorStatus.equals("Inactive")){
            assignStatus = "Inactive";
            return "Inactive";
            
        }
        return "Active";
        
    }
    
    public String[] getAssignmentDataList(){
        String[] dataLine = {assignID,supervisorID,supervisorName,tpNum,studentName,intakeCode,course,assignStatus};
        return dataLine;
    }
    
    
}
