package com.philips.pins.robotium.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.philips.pins.robotium.demo.utils.StringUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameEd;
    private EditText passwordEd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        sharedPreferences = getSharedPreferences(StringUtils.PROFILE, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //If the number of users has not been initialized, let it be 0
        int count = sharedPreferences.getInt(StringUtils.COUNT, -1);
        if (count == -1) {
            editor.putInt(StringUtils.COUNT,0);
            editor.commit();
        }
        //Display the last account logged successfully
        if (!sharedPreferences.getString(StringUtils.USERNAME,"").equals("")) {
            usernameEd.setText(sharedPreferences.getString(StringUtils.USERNAME, ""));
        }
    }

    //Initialize the controls
    private void initView() {
        usernameEd = (EditText) findViewById(R.id.ed_username);
        passwordEd = (EditText) findViewById(R.id.ed_password);
    }

    @Override
    public void onClick(View v) {
        String username = usernameEd.getText().toString();
        String password = passwordEd.getText().toString();
        int count = sharedPreferences.getInt(StringUtils.COUNT, 0);

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(),StringUtils.EMPTY_TOAST_USERNAME,Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),StringUtils.EMPTY_TOAST_PASSWORD,Toast.LENGTH_SHORT).show();
        } else {
            switch (v.getId()) {
                case R.id.login_button:
                    if (getUserNumber(count, username) == -1) {
                        Toast.makeText(getApplicationContext(), StringUtils.NONEXISTENT_USER,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String correctPass = sharedPreferences.getString(StringUtils.PASSWORD + getUserNumber(count, username), "");
                        if (password.equals(correctPass))
                            Toast.makeText(getApplicationContext(), StringUtils.LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), StringUtils.LOGIN_FAIL, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.regist_button:
                    if (count != 0) {
                        //Judge whether the username is already registered
                        if (isReused(count, username)) {
                            Toast.makeText(getApplicationContext(), StringUtils.USERNAME_REUSED,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //Add a new user
                            addUser(count, username, password);
                        }
                    } else {
                        //Add a new user
                        addUser(count, username, password);
                    }
                    break;
            }
        }
    }

    public void addUser(int count, String username, String password){
        count += 1;
        editor.putString(StringUtils.USERNAME + count, username);
        editor.putString(StringUtils.PASSWORD + count, password);
        editor.putInt(StringUtils.COUNT, count);
        editor.putString(StringUtils.USERNAME, username);
        editor.commit();
        Toast.makeText(getApplicationContext(), StringUtils.REGIST_SUCCESS, Toast.LENGTH_SHORT).show();
    }

    /**
     * Determine whether the username is already registered
     * @param count the number of users
     * @param username
     * @return
     */
    public boolean isReused(int count, String username) {
        for (int i = 1;i <= count;i++) {
            String currentUsername = sharedPreferences.getString(StringUtils.USERNAME + i,"");
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
                String currentUsername = sharedPreferences.getString(StringUtils.USERNAME + i,"");
                if (username.equals(currentUsername)) {
                    break;
                }
            }
            return i;
        } else return -1;
    }

}
