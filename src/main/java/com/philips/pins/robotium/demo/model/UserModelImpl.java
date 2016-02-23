package com.philips.pins.robotium.demo.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.philips.pins.robotium.demo.R;


/**
 * Created by dxx on 2016/2/22.
 */
public class UserModelImpl implements IUserModel {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserModelImpl(SharedPreferences sp, Context c){
        this.sp = sp;
        this.editor = sp.edit();
        context = c;
        editor = sp.edit();
        //If the number of users has not been initialized, let it be 0
        int count = sp.getInt(c.getString(R.string.count), -1);
        if (count == -1) {
            editor.putInt(c.getString(R.string.count), 0);
            editor.commit();
        }
    }

    @Override
    public boolean regist(String username, String password, int count) {
        if (count != 0) {
            //Judge whether the username is already registered
            if (isReused(count, username)) {
                return false;
            } else {
                //Add a new user
                addUser(count, username, password);
                return true;
            }
        } else {
            //Add a new user
            addUser(count, username, password);
            return true;
        }
    }

    @Override
    public int login(String username, String password, int count) {
        if (getUserNumber(count, username) == -1) {
            return -1;
        } else {
            String correctPass = sp.getString(context.getString(R.string.password) + getUserNumber(count, username), "");
            if (password.equals(correctPass)) {
                editor.putString(context.getString(R.string.user), username);
                editor.commit();
                return 1;
            } else {
                return 0;
            }
        }
    }


    public void addUser(int count, String username, String password){
        //因为没有使用数组和列表来存储用户数据，所以只是将JavaBean创建了出来，没有进行实际存储操作
        count += 1;
        Log.d("Present User count is ", count+"");
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
