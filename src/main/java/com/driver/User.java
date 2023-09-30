package com.driver;

public class User {

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public User() {
    }

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    private String name;
    private String mobile;
}
