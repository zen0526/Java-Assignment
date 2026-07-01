/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopassignment;


public class Feedback {
    private String tpNum;
    private String supervisorID;
    private String date;
    private String time;
    private String status;
    private String feedback;
    



public Feedback(String tpNum,String supervisorID, String date, String time, String feedbackText, String status) {
        this.tpNum = tpNum;
        this.supervisorID = supervisorID;
        this.date = date;
        this.time = time;
        this.status = feedbackText;
        this.feedback = status;
    }

    // Getter 和 Setter
    public String getTpNum() {
        return tpNum;
    }
    
    public String getsupervisorID() {
        return supervisorID;
    }
    
    public void setsupervisorID(String supervisorID) {
         this.supervisorID = supervisorID;
    }

    public void setTpNum(String tpNum) {
        this.tpNum = tpNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFeedbackText() {
        return feedback;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedback = feedbackText;
    }

    public String getstatus() {
        return status;
    }
    
    public void setstatus(String status){
        this.status = status;
    }

   public void setFeedback(String feedback) { this.feedback = feedback; }

    public String toFileString() {
        return tpNum + "," + supervisorID + "," + date + "," + time + "," + status + "," + feedback;
    }
}

    
