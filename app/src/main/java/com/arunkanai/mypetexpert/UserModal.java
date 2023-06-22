package com.arunkanai.mypetexpert;

import java.io.Serializable;

public class UserModal implements Serializable

{
    private String username;
    private String emailaddress;
    private String gender;


    UserModal(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
