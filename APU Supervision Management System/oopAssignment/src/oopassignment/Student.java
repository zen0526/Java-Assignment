package oopassignment;
public class Student extends User {
    private String tpNum;
    private String country;
    private String address;
    private String intakeCode;
    private String course;
    
    public Student(String tpNum, String name, String password, String email, String phoneNum, String gender, String dob, String address, String country, String intakeCode, String course, String status){
        super(email, password, name, "Student", gender, dob, phoneNum, status);
        this.tpNum = tpNum;
        this.country = country;
        this.address = address;
        this.intakeCode = intakeCode;
        this.course = course;
    }
    
    // Getters
    public String gettpNum(){
        return tpNum;
    }
    
    public String getCountry(){
        return country;
    }
    
    public String getAddress(){
        return address;
    }
    
    public String getintakecode(){
        return intakeCode;
    }
    
    public String getCourse(){
        return course;
    }
    
    
    // Setters
    
    public void settpNum(String tpNum){
        this.tpNum = tpNum;
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public void setintakecode(String intakeCode){
        this.intakeCode = intakeCode;
    }
    
    public void setCourse(String course){
        this.course = course;
    }
    
    public String[] getStudentDataList(){
        // {"TP Number","Name","StudentIntake","Course","Status"};
        String[] dataLine = {tpNum,super.getName(),intakeCode,course,super.getStatus()};
        return dataLine;
    }
    
}
