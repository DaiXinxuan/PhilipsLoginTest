package com.philips.pins.robotium.demo.presenter;

import android.content.SharedPreferences;

/**
 * Created by dxx on 2016/2/22.
 */
public interface IUserPresenter {
    boolean regist(String username, String password, int count);
    int login(String username, String password, int count);
    void display();
    SharedPreferences getSharedPreference();
}
