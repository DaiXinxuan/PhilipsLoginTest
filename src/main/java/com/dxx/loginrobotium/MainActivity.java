package com.dxx.loginrobotium;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameEd;
    private EditText passwordEd;
    private Button loginBtn;
    private Button registBtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        sharedPreferences = getSharedPreferences("profile", Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //If the number of users has not been initialized, let it be 0
        int count = sharedPreferences.getInt("count", -1);
        if (count == -1) {
            editor.putInt("count",0);
            editor.commit();
        }
        //Display the last account logged successfully
        if (!sharedPreferences.getString("username","").equals("")) {
            usernameEd.setText(sharedPreferences.getString("username", ""));
        }
    }

    //Initialize the controls
    private void initView() {
        usernameEd = (EditText) findViewById(R.id.ed_username);
        passwordEd = (EditText) findViewById(R.id.ed_password);
        loginBtn = (Button) findViewById(R.id.login_button);
        registBtn = (Button) findViewById(R.id.regist_button);
    }

    @Override
    public void onClick(View v) {
        String username = usernameEd.getText().toString();
        String password = passwordEd.getText().toString();
        int count = sharedPreferences.getInt("count", 0);

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(),"Username can not be empty",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),"Password can not be empty",Toast.LENGTH_SHORT).show();
        } else {
            switch (v.getId()) {
                case R.id.login_button:
                    if (getUserNumber(count, username) == -1) {
                        Toast.makeText(getApplicationContext(), "The username does not exist",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String correctPass = sharedPreferences.getString("password" + getUserNumber(count, username), "");
                        if (password.equals(correctPass))
                            Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.regist_button:
                    if (count != 0) {
                        //Judge whether the username is already registered
                        if (isReused(count, username)) {
                            Toast.makeText(getApplicationContext(), "Username has already been registered",
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
        editor.putString("username" + count, username);
        editor.putString("password" + count, password);
        editor.putInt("count", count);
        editor.putString("username", username);
        editor.commit();
        Toast.makeText(getApplicationContext(), "Regist successfully", Toast.LENGTH_SHORT).show();
    }

    /**
     * Determine whether the username is already registered
     * @param count the number of users
     * @param username
     * @return
     */
    public boolean isReused(int count, String username) {
        for (int i = 1;i <= count;i++) {
            String currentUsername = sharedPreferences.getString("username"+i,"");
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
                String currentUsername = sharedPreferences.getString("username"+i,"");
                if (username.equals(currentUsername)) {
                    break;
                }
            }
            return i;
        } else return -1;
    }

}
