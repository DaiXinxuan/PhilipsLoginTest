package com.philips.pins.robotium.demo.model;


import com.philips.pins.robotium.demo.bean.UserBean;

/**
 * Created by dxx on 2016/2/22.
 */
public interface UserModel {
    void addUser(int count, UserBean userBean);
    boolean isReused(int count, String username);
    int getUserNumber(int count, String username);
}
