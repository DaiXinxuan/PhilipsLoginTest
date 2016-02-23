package com.philips.pins.robotium.demo.model;


/**
 * Created by dxx on 2016/2/22.
 */
public interface IUserModel {
    boolean regist(String username, String password, int count);
    int login(String username, String password, int count);
}
