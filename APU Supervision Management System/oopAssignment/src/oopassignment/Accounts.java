package oopassignment;
public class Accounts {
    private String accountID, role, attempts;
    
    public Accounts(String accountID, String role, String attempts){
        this.accountID = accountID;
        this.role = role;
        this.attempts = attempts;
    }
    
    //Getters
    public String getId(){
        return accountID;
    }
    
    public String getRole(){
        return role;
    }
    
    public String getAttempts(){
        return attempts;
    }
    
    //Setters
    public void setID(String accountID){
        this.accountID = accountID;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public void setAttempts(String attempts){
        this.attempts = attempts;
    }
}
