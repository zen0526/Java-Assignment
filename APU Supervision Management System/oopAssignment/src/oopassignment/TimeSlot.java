
package oopassignment;


public class TimeSlot {
    private String supervisorId;
    private String date;
    private String time;
    private String status;

    // 
    public TimeSlot(String supervisorId, String date, String time,String status) {
        this.supervisorId = supervisorId;
        this.date = date;
        this.time = time;
        this.status = status;
    }
    
    
    

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String toFileFormat() {
        return supervisorId + "," + date + "," + time + "," + status;
    }
    
}



