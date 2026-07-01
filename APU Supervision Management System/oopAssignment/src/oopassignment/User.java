package oopassignment;
public class User {
    private String email;
    private String password;
    private String name;
    private String role;
    private String gender;
    private String dob;
    private String phoneNum;
    private String status;    
    
    
    // Constructor
    public User(String email, String password, String name, String role, String gender, String dob, String phoneNum, String status){
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.dob = dob;
        this.phoneNum = phoneNum;
        this.status = status;

    }
    
    // Getters
    public String getEmail(){
        return email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getName(){
        return name;
    }
    
    public String getRole(){
        return role;
    }
    
    public String getGender(){
        return gender;
    }
    
    public String getdob(){
        return dob;
    }    
    
    public String getphoneNum(){
        return phoneNum;
    }    
    
    public String getStatus(){
        return status;
    }    
    
    // Setters
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }
    
    public void setdob(String dob){
        this.dob = dob;
    }    
    
    public void setphoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }    
    
    public void setStatus(String status){
        this.status = status;
    }    
}
