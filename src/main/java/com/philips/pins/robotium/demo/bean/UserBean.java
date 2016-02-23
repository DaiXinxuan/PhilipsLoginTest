package com.philips.pins.robotium.demo.bean;

/**
 * Created by dxx on 2016/2/22.
 */
public class UserBean {
    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    private String username;
    private String password;

    public UserBean(String username, String password){
        this.username = username;
        this.password = password;
    }
}
