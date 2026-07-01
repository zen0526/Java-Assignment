/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopassignment;

import oopassignment.Assignment;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class FA_TableManager {

    
    public static void createObjectValues(ArrayList<Supervisor> supervisorList, ArrayList<Student> studentList, ArrayList<Assignment> assignmentList){
        ArrayList<String[]> supervisorData = FA_FileHandling.readFile("supervisor.txt");
        for (String[] supervisor : supervisorData){
            //set important supervisor data for assignment in objects
            Supervisor supervisorObject = new Supervisor(supervisor[0], supervisor[1], supervisor[2], supervisor[3], supervisor[4], supervisor[5], supervisor[6], supervisor[7], supervisor[8], supervisor[9], supervisor[10]);
            supervisorList.add(supervisorObject);
        }
        
        ArrayList<String[]> studentData = FA_FileHandling.readFile("students.txt");
        for (String[] student : studentData){
            //set important student data for assignment in objects
            Student studentObject = new Student(
                    //Dont include login Attempts
                    student[0],student[1],student[2],student[3],student[4],student[5],student[6],student[7],student[8],student[9],student[10],student[11]
            );
            studentList.add(studentObject);
        }
        
        ArrayList<String[]> assignmentData = FA_FileHandling.readFile("assignment.txt");
        boolean assignmentfileRewriteNeeded = false;
        for (String[] assignment : assignmentData){
            Assignment assignmentObject = new Assignment(assignment[0], assignment[1], assignment[2], assignment[3], assignment[4],assignment[5],assignment[6],assignment[7]);
            //Check if either supervisor or student are inactive
            if (assignmentObject.verifyAssignStatus(supervisorList, studentList).equals("Inactive")){
                assignmentfileRewriteNeeded = true;
            }
            assignmentList.add(assignmentObject);
        }
        if (assignmentfileRewriteNeeded == true){
                FA_FileHandling.writeAssignmentFile("assignment.txt", assignmentList);
        }
        
        //Counting and Updating number of students for Supervisor
        for(Supervisor supervisor : supervisorList){
            supervisor.countSupervisorNumberOfStudent(assignmentList);
        }
        FA_FileHandling.writeSupervisorFile("supervisor.txt",supervisorList);
        

    
    }

    public static void displayAssignmentTable(DefaultTableModel modelAssignment, ArrayList<Assignment> assignmentList) {
        modelAssignment.setRowCount(0);
        //Add Rows from assignment object values to Table
        for (Assignment data : assignmentList) {
            modelAssignment.addRow(data.getAssignmentDataList());
        }
    }

    public static void displayStudentTable(DefaultTableModel modelStudent, ArrayList<Student> studentList) {
        modelStudent.setRowCount(0);
        //Add Rows from student objects value to Table
        for (Student student : studentList) {
            modelStudent.addRow(student.getStudentDataList());
        }
    }

    public static void displaySupervisorTable(DefaultTableModel modelSupervisor, ArrayList<Supervisor> supervisorList) {
        modelSupervisor.setRowCount(0);
        //Add Rows from supervisor objects values to Table
        for (Supervisor supervisor : supervisorList) {
            modelSupervisor.addRow(supervisor.getSupervisorDataList());
        }
    }
}
