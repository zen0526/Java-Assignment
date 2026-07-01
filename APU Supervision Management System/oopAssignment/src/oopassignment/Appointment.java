package oopassignment;

public class Appointment {
    private String tpNum;
    private String supervisorID;
    private String date;
    private String time;
    private String status;
    private String feedback;


    public Appointment(String tpNum, String supervisorID, 
           String date, String time, String status, String feedback) {
        this.tpNum = tpNum;
        this.supervisorID = supervisorID;
        this.date = date;
        this.time = time;
        this.status = status;
        this.feedback = feedback;
    }


    
    public String getTpNum(){
        return tpNum; 
    }
    
    public String getSupervisorID(){ 
        return supervisorID; 
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
    
    public String getStatus(){ 
        return status; 
    }
    
    public String getFeedback(){ 
        return feedback; 
    }

    
    public void setTpNum(String tpNum){ 
        this.tpNum = tpNum; 
    }
    
    public void setSupervisorID(String supervisorID){
        this.supervisorID = supervisorID; 
    }
    
    
    public void setStatus(String status){ 
        this.status = status; 
    }
    
    public void setFeedback(String feedback){ 
        this.feedback = feedback; 
    }


    @Override
    public String toString() {
        return tpNum + "-" + supervisorID + 
               "-" + date + "-" + time + "-" + status + "-" + feedback;
    }
    

}

