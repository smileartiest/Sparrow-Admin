package com.smile.atozadmin.parameters;

public class UserParameters {
    String name,email,phno,fcm;

    public UserParameters() {
    }

    public UserParameters(String name, String email, String phno, String fcm) {
        this.name = name;
        this.email = email;
        this.phno = phno;
        this.fcm = fcm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }
}
