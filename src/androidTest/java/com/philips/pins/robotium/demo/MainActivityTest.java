package com.philips.pins.robotium.demo;

import android.test.ActivityInstrumentationTestCase2;

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
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


    public void testAUsernameEmpty() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        //Hides the soft keyboard
        solo.hideSoftKeyboard();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText("Username can not be empty");
        assertTrue("Username can be empty", actual);
    }

    public void testBPasswordEmpty() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        //Hides the soft keyboard
        solo.hideSoftKeyboard();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.enterText(0, "Test");
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText("Password can not be empty");
        assertTrue("Password can be empty", actual);
    }

    public void testCAddNewUser() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        solo.hideSoftKeyboard();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "12345");
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText("Regist successfully");
        assertTrue("Regist failed", actual);
    }

    public void testDAddExistingUser() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        solo.hideSoftKeyboard();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "1234");
        solo.clickOnButton("Registration");
        boolean actual = solo.searchText("Regist successfully");
        assertFalse("Existing username still can be registed", actual);
    }

    public void testELoginExistingUser() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        solo.hideSoftKeyboard();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "12345");
        solo.clickOnButton("Login");
        boolean actual = solo.searchText("Successful login");
        assertTrue("Login failed!", actual);
    }

    public void testFLoginNotExistingUser() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        solo.hideSoftKeyboard();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.enterText(0, "daixinxuan12345890");
        solo.enterText(1, "12345");
        solo.clickOnButton("Login");
        boolean actual = solo.searchText("The username does not exist");
        assertTrue("The user doesn't exist can login", actual);
    }

    public void testGLoginWithWrongPass () throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        solo.hideSoftKeyboard();
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.enterText(0, "miniTest2");
        solo.enterText(1, "123456");
        solo.clickOnButton("Login");
        boolean actual = solo.searchText("Login failed");
        assertTrue("Username with wrong password also can login", actual);
    }

}