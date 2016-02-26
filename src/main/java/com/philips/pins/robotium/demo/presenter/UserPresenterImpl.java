package com.philips.pins.robotium.demo.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.philips.pins.robotium.demo.R;
import com.philips.pins.robotium.demo.bean.UserBean;
import com.philips.pins.robotium.demo.model.UserModel;
import com.philips.pins.robotium.demo.model.UserModelImpl;
import com.philips.pins.robotium.demo.view.LoginView;

/**
 * Created by dxx on 2016/2/22.
 */
public class UserPresenterImpl implements UserPresenter {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private LoginView iLoginView;
    private UserModel userModel;
    private Context c;

    public UserPresenterImpl(LoginView iLoginView, Context context) {
        this.iLoginView = iLoginView;
        this.c = context;
        this.sp = c.getSharedPreferences(c.getString(R.string.profile), Activity.MODE_PRIVATE);
        editor = sp.edit();
        //If the number of users has not been initialized, let it be 0
        int count = sp.getInt(c.getString(R.string.count), -1);
        if (count == -1) {
            editor.putInt(c.getString(R.string.count), 0);
            editor.commit();
        }
        userModel = new UserModelImpl(context);
    }

    @Override
    public boolean regist(UserBean userBean, int count) {
        String username = userBean.getUsername();
        if (count != 0) {
            //Judge whether the username is already registered
            if (userModel.isReused(count, username)) {
                iLoginView.showMessage(c.getString(R.string.user_resued));
                return false;
            } else {
                //Add a new user
                userModel.addUser(count, userBean);
                iLoginView.showMessage(c.getString(R.string.regist_success));
                return true;
            }
        } else {
            //Add a new user
            userModel.addUser(count, userBean);
            iLoginView.showMessage(c.getString(R.string.regist_success));
            return true;
        }
    }

    @Override
    public int login(UserBean userBean, int count) {
        String username = userBean.getUsername();
        String password = userBean.getPassword();
        if (userModel.getUserNumber(count, username) == -1) {
            iLoginView.showMessage(c.getString(R.string.nonexisten_user));
            return -1;
        } else {
            String correctPass = sp.getString(c.getString(R.string.password) + userModel.getUserNumber(count, username), "");
            if (password.equals(correctPass)) {
                editor.putString(c.getString(R.string.user), username);
                editor.commit();
                iLoginView.showMessage(c.getString(R.string.login_success));
                return 1;
            } else {
                iLoginView.showMessage(c.getString(R.string.login_fail));
                return 0;
            }
        }
    }

    @Override
    public void displayLastLoginUsername() {
        if (!sp.getString(c.getString(R.string.user),"").equals("")) {
            iLoginView.setUsername(sp);
        }
    }

    @Override
    public SharedPreferences getSharedPreference() {
        return sp;
    }
}
