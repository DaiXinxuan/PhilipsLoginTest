package com.philips.pins.robotium.demo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

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
        solo.clearEditText((EditText)solo.getView(R.id.username));
        solo.clearEditText((EditText)solo.getView(R.id.password));
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


    public void testUsernameEmpty() throws Exception {
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(StringUtils.EMPTY_TOAST_USERNAME);
        assertTrue(StringUtils.USERNAME_EMPTY_TEST, actual);
    }

    public void testPasswordEmpty() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username),"Test");
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(StringUtils.EMPTY_TOAST_PASSWORD);
        assertTrue(StringUtils.PASSWORD_EMPTY_TEST, actual);
    }

    public void testAddNewUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText)solo.getView(R.id.password), "12345");
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(StringUtils.REGIST_SUCCESS);
        assertTrue(StringUtils.ADD_USER_TEST, actual);
    }

    public void testAddExistingUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText)solo.getView(R.id.password), "1234");
        solo.clickOnView(solo.getView(R.id.regist));
        boolean actual = solo.searchText(StringUtils.REGIST_SUCCESS);
        assertFalse(StringUtils.ADD_EXISTING_USER_TEST, actual);
    }

    public void testLoginExistingUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText)solo.getView(R.id.password), "12345");
        solo.clickOnView(solo.getView(R.id.login));
        boolean actual = solo.searchText(StringUtils.LOGIN_SUCCESS);
        assertTrue(StringUtils.LOGIN_FAIL, actual);
    }

    public void testLoginNotExistingUser() throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "daixinxuan12345890");
        solo.enterText((EditText)solo.getView(R.id.password), "12345");
        solo.clickOnView(solo.getView(R.id.login));
        boolean actual = solo.searchText(StringUtils.NONEXISTENT_USER);
        assertTrue(StringUtils.NONEXISTENT_USER_LOGIN, actual);
    }

    public void testLoginWithWrongPass () throws Exception {
        solo.enterText((EditText)solo.getView(R.id.username), "miniTest2");
        solo.enterText((EditText)solo.getView(R.id.password), "123456");
        solo.clickOnView(solo.getView(R.id.login));
        boolean actual = solo.searchText(StringUtils.LOGIN_FAIL);
        assertTrue(StringUtils.WRONGPASS_USER_LOGIN, actual);
    }

}
