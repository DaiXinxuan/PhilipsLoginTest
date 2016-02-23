package com.philips.pins.robotium.demo.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.philips.pins.robotium.demo.R;
import com.philips.pins.robotium.demo.model.IUserModel;
import com.philips.pins.robotium.demo.model.UserModelImpl;
import com.philips.pins.robotium.demo.view.ILoginView;

/**
 * Created by dxx on 2016/2/22.
 */
public class UserPresenterImpl implements IUserPresenter{
    private SharedPreferences sp;
    private ILoginView iLoginView;
    private IUserModel userModel;
    private Context c;

    public UserPresenterImpl(ILoginView iLoginView, Context context) {
        this.iLoginView = iLoginView;
        this.c = context;
        this.sp = c.getSharedPreferences(c.getString(R.string.profile), Activity.MODE_PRIVATE);
        userModel = new UserModelImpl(sp, context);
    }

    @Override
    public boolean regist(String username, String password, int count) {
        boolean bool = userModel.regist(username, password, count);
        if (bool) {
            iLoginView.showToast(c.getString(R.string.regist_success));
        }else iLoginView.showToast(c.getString(R.string.user_resued));
        return bool;
    }

    @Override
    public int login(String username, String password, int count) {
        int i = userModel.login(username, password, count);
        if (i == -1){
            iLoginView.showToast(c.getString(R.string.nonexisten_user));
        } else if (i == 1) {
            iLoginView.showToast(c.getString(R.string.login_success));
        } else if (i == 0) {
            iLoginView.showToast(c.getString(R.string.login_fail));
        }
        return i;
    }

    @Override
    public void display() {
        if (!sp.getString(c.getString(R.string.user),"").equals("")) {
            iLoginView.setUsername(sp);
        }
    }

    @Override
    public SharedPreferences getSharedPreference() {
        return sp;
    }
}
