package com.philips.pins.robotium.demo.presenter;

import android.content.SharedPreferences;

import com.philips.pins.robotium.demo.bean.UserBean;

/**
 * Created by dxx on 2016/2/22.
 */
public interface UserPresenter {
    boolean regist(UserBean userBean, int count);
    int login(UserBean userBean, int count);
    void displayLastLoginUsername();
    SharedPreferences getSharedPreference();
}
