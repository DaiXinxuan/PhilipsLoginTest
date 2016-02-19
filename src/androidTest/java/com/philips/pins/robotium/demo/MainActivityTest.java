package com.philips.pins.robotium.demo;

import android.test.ActivityInstrumentationTestCase2;

import com.philips.pins.robotium.demo.utils.StringUtils;
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
        solo.clearEditText(0);
        solo.clearEditText(1);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


    public void testUsernameEmpty() throws Exception {
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText(StringUtils.EMPTY_TOAST_USERNAME);
        assertTrue(StringUtils.USERNAME_EMPTY_TEST, actual);
    }

    public void testPasswordEmpty() throws Exception {
        solo.enterText(0, "Test");
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText(StringUtils.EMPTY_TOAST_PASSWORD);
        assertTrue(StringUtils.PASSWORD_EMPTY_TEST, actual);
    }

    public void testAddNewUser() throws Exception {
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "12345");
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText(StringUtils.REGIST_SUCCESS);
        assertTrue(StringUtils.ADD_USER_TEST, actual);
    }

    public void testAddExistingUser() throws Exception {
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "1234");
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText(StringUtils.REGIST_SUCCESS);
        assertFalse(StringUtils.ADD_EXISTING_USER_TEST, actual);
    }

    public void testLoginExistingUser() throws Exception {
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "12345");
        solo.clickOnButton("Login");
        boolean actual = solo.searchText(StringUtils.LOGIN_SUCCESS);
        assertTrue(StringUtils.LOGIN_FAIL, actual);
    }

    public void testLoginNotExistingUser() throws Exception {
        solo.enterText(0, "daixinxuan12345890");
        solo.enterText(1, "12345");
        solo.clickOnButton("Login");
        boolean actual = solo.searchText(StringUtils.NONEXISTENT_USER);
        assertTrue(StringUtils.NONEXISTENT_USER_LOGIN, actual);
    }

    public void testLoginWithWrongPass () throws Exception {
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "123456");
        solo.clickOnButton("Login");
        boolean actual = solo.searchText(StringUtils.LOGIN_FAIL);
        assertTrue(StringUtils.WRONGPASS_USER_LOGIN, actual);
    }

}
