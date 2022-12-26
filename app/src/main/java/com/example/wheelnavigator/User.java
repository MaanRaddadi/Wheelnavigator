

package com.example.wheelnavigator;

public class User {

    private String email, password, username, userid;
    private boolean disability ;


    public User(String email, String password, String username, boolean disability) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.disability = disability;
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDisability() {
        return disability;
    }

    public String getUserid() {

        return userid;
    }

    public User() {

    }
}
