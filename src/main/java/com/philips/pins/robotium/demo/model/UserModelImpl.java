package com.philips.pins.robotium.demo.model;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.philips.pins.robotium.demo.R;
import com.philips.pins.robotium.demo.bean.UserBean;


/**
 * Created by dxx on 2016/2/22.
 */
public class UserModelImpl implements UserModel {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserModelImpl(Context c){
        context = c;
        this.sp = c.getSharedPreferences(c.getString(R.string.profile), Activity.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public void addUser(int count, UserBean userBean){
        String username = userBean.getUsername();
        String password = userBean.getPassword();
        count += 1;
        editor.putString(context.getString(R.string.user) + count, username);
        editor.putString(context.getString(R.string.password) + count, password);
        editor.putInt(context.getString(R.string.count), count);
        editor.commit();
    }

    /**
     * Determine whether the username is already registered
     * @param count the number of users
     * @param username
     * @return
     */
    public boolean isReused(int count, String username) {
        for (int i = 1;i <= count;i++) {
            String currentUsername = sp.getString(context.getString(R.string.user) + i,"");
            if (username.equals(currentUsername)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get this user's number
     * @param count the number of users
     * @param username
     * @return
     */
    public int getUserNumber(int count, String username) {
        int i = 1;
        if(isReused(count, username)) {
            for (i = 1;i <= count;i++) {
                String currentUsername = sp.getString(context.getString(R.string.user) + i,"");
                if (username.equals(currentUsername)) {
                    break;
                }
            }
            return i;
        } else return -1;
    }

}
