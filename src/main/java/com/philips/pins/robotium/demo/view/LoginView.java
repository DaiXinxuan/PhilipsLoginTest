package com.philips.pins.robotium.demo.view;

import android.content.SharedPreferences;

/**
 * Created by dxx on 2016/2/22.
 */
public interface LoginView {
    String getUsername();
    String getPassword();

    void setUsername(SharedPreferences sp);
    void showMessage(String message);
}
