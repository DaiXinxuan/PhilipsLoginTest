package com.philips.pins.robotium.demo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by dxx on 2016/2/18.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        //Unlock the lock screen
        solo.unlockScreen();
        //Hides the soft keyboard
        solo.hideSoftKeyboard();
        solo.clearEditText((EditText)solo.getView(R.id.username));
        solo.clearEditText((EditText)solo.getView(R.id.password));
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


    public void testUsernameEmpty() throws Exception {
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(solo.getString(R.string.empty_user));
        assertTrue(solo.getString(R.string.user_empty_test), actual);
    }

    public void testPasswordEmpty() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username),"Test");
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(solo.getString(R.string.empty_pass));
        assertTrue(solo.getString(R.string.pass_empty_test), actual);
    }

    public void testAddNewUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText)solo.getView(R.id.password), "12345");
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(solo.getString(R.string.regist_success));
        assertTrue(solo.getString(R.string.add_user_test), actual);
    }

    public void testAddExistingUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText)solo.getView(R.id.password), "1234");
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(solo.getString(R.string.regist_success));
        assertFalse(solo.getString(R.string.add_existing_user_test), actual);
    }

    public void testLoginExistingUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText)solo.getView(R.id.password), "12345");
        solo.clickOnView(solo.getView(R.id.login));
        boolean actual = solo.searchText(solo.getString(R.string.login_success));
        assertTrue(solo.getString(R.string.login_fail), actual);
    }

    public void testLoginNotExistingUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "daixinxuan12345890");
        solo.enterText((EditText)solo.getView(R.id.password), "12345");
        solo.clickOnView(solo.getView(R.id.login));
        boolean actual = solo.searchText(solo.getString(R.string.nonexisten_user));
        assertTrue(solo.getString(R.string.nonexistent_user_login), actual);
    }

    public void testLoginWithWrongPass () throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText) solo.getView(R.id.password), "123456");
        solo.clickOnView(solo.getView(R.id.login));
        boolean actual = solo.searchText(solo.getString(R.string.login_fail));
        assertTrue(solo.getString(R.string.wrongpass_user_login), actual);
    }

    public void testLogin() throws Exception {
    }
}
