package com.example.todoList;

public class User {

    private String firstName;
    private String lastName;
    private String Username;
    private String password;
    private String PhoneNumber;

    public User(){
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String fName) {
        this.firstName = fName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lName) {
        this.lastName = lName;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
