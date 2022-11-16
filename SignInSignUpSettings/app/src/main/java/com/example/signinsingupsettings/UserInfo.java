package com.example.signinsingupsettings;

public class UserInfo {
    public String firstname, lastname, email, username, password;

    public UserInfo(){}

    public UserInfo(String firstname, String lastname, String username, String email, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email= email;
        this.password = password;
        this.username = username;
    }
}
