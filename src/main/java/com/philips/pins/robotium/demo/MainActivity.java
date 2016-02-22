package com.philips.pins.robotium.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameEd;
    private EditText passwordEd;
    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        sharedPreferences = getSharedPreferences(getString(R.string.profile), Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //If the number of users has not been initialized, let it be 0
        int count = sharedPreferences.getInt(getString(R.string.count), -1);
        if (count == -1) {
            editor.putInt(getString(R.string.count),0);
            editor.commit();
        }
        //Display the last account logged successfully
        if (!sharedPreferences.getString(getString(R.string.user),"").equals("")) {
            usernameEd.setText(sharedPreferences.getString(getString(R.string.user), ""));
        }
    }

    //Initialize the controls
    private void initView() {
        usernameEd = (EditText) findViewById(R.id.username);
        passwordEd = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        String username = usernameEd.getText().toString();
        String password = passwordEd.getText().toString();
        int count = sharedPreferences.getInt(getString(R.string.count), 0);

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(),getString(R.string.empty_user),Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),getString(R.string.empty_pass),Toast.LENGTH_SHORT).show();
        } else {
            switch (v.getId()) {
                case R.id.login:
                    login(username, password, count);
                    break;
                case R.id.regist:
                    regist(username, password, count);
                    break;
            }
        }
    }

    public void addUser(int count, String username, String password){
        count += 1;
        editor.putString(getString(R.string.user) + count, username);
        editor.putString(getString(R.string.password) + count, password);
        editor.putInt(getString(R.string.count), count);
        editor.putString(getString(R.string.user), username);
        editor.commit();
        Toast.makeText(getApplicationContext(), getString(R.string.regist_success), Toast.LENGTH_SHORT).show();
    }

    public boolean regist(String username, String password, int count){
        if (count != 0) {
            //Judge whether the username is already registered
            if (isReused(count, username)) {
                Toast.makeText(getApplicationContext(), getString(R.string.user_resued),
                        Toast.LENGTH_SHORT).show();
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

    /**
     *
     * @param username
     * @param password
     * @param count
     * @return
     */
    public boolean login(String username, String password, int count){
        if (getUserNumber(count, username) == -1) {
            Toast.makeText(getApplicationContext(), getString(R.string.nonexisten_user),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String correctPass = sharedPreferences.getString(getString(R.string.password) + getUserNumber(count, username), "");
            if (password.equals(correctPass)) {
                Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    /**
     * Determine whether the username is already registered
     * @param count the number of users
     * @param username
     * @return
     */
    public boolean isReused(int count, String username) {
        for (int i = 1;i <= count;i++) {
            String currentUsername = sharedPreferences.getString(getString(R.string.user) + i,"");
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
                String currentUsername = sharedPreferences.getString(getString(R.string.user) + i,"");
                if (username.equals(currentUsername)) {
                    break;
                }
            }
            return i;
        } else return -1;
    }

}
