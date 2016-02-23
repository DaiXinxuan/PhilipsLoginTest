package com.philips.pins.robotium.demo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.philips.pins.robotium.demo.presenter.IUserPresenter;
import com.philips.pins.robotium.demo.presenter.UserPresenterImpl;
import com.philips.pins.robotium.demo.view.ILoginView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ILoginView{
    private EditText usernameEd;
    private EditText passwordEd;
    private IUserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        userPresenter = new UserPresenterImpl(this, getApplicationContext());

        //Display the last account logged successfully
        userPresenter.display();
    }

    //Initialize the controls
    private void initView() {
        usernameEd = (EditText) findViewById(R.id.username);
        passwordEd = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        String username = getUsername();
        String password = getPassword();
        int count = userPresenter.getSharedPreference().getInt(getString(R.string.count), 0);

        if (TextUtils.isEmpty(username)) {
            showToast(getString(R.string.empty_user));
        } else if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.empty_pass));
        } else {
            switch (v.getId()) {
                case R.id.login:
                    userPresenter.login(username, password, count);
                    break;
                case R.id.regist:
                    userPresenter.regist(username, password, count);
                    break;
            }
        }
    }


    @Override
    public String getUsername() {
        return usernameEd.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEd.getText().toString();
    }

    @Override
    public void setUsername(SharedPreferences sp) {
        usernameEd.setText(sp.getString(getString(R.string.user), ""));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
