package Modules;

import java.sql.Timestamp;
public class Users {
    private int balance , age, securityCode;
    private String phone , email,password , userName;
   private Timestamp dateOfCreateAccount;

    public String getNationalUserId() {
        return NationalUserId;
    }

    public void setNationalUserId(String nationalUserId) {
        NationalUserId = nationalUserId;
    }
    private String NationalUserId ;

    public Users(int balance, int age, int securityCode, String phone, String email, String password, String userName, Timestamp dateOfCreateAccount, String nationalUserId, int userId, StringBuilder activities) {
        this.balance = balance;
        this.age = age;
        this.securityCode = securityCode;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.dateOfCreateAccount = dateOfCreateAccount;
        this.NationalUserId = nationalUserId;
        this.userId = userId;
        this.activities = activities;
    }
    public Users(int id, int balance , int age, int securityCode, String phone , String email , String password, String userName){
       setPassword(password);
       setUserId(id);
       setSecurityCode(securityCode);
       setPhone(phone);
       setEmail(email);
       setBalance(balance);
       setAge(age);
       setUserName(userName);
   }

    public Users(int balance, int age, int securityCode, String phone, String email, String password, String userName, Timestamp dateOfCreateAccount, int userId){
        this.balance = balance;
        this.age = age;
        this.securityCode = securityCode;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.dateOfCreateAccount = dateOfCreateAccount;
        this.userId = userId;
    }

    public Users(String email, String passowrd, int sercurityCode){
       this.setPassword(passowrd);
       this.setSecurityCode(sercurityCode);
       this.setEmail(email);
}
    public Users(String email, String passowrd){
        this.setPassword(passowrd);
        this.setEmail(email);
    }
   public Users(String userName , String password , String email ,String phone, int age , int securityCode){
this.setUserName(userName);
this.setAge(age);
this.setEmail(email);
this.setPhone(phone);
this.setSecurityCode(securityCode);
this.setPassword(password);
   }
    private int userId;
private StringBuilder activities;

    public Users() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public StringBuilder getActivities() {
        return activities;
    }

    public void setActivities(StringBuilder activities) {
        this.activities = activities;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getDateOfCreateAccount() {
        return dateOfCreateAccount;
    }

    public void setDateOfCreateAccount(Timestamp dateOfCreateAccount) {
        this.dateOfCreateAccount = dateOfCreateAccount;
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
