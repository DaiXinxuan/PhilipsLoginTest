# Summary about Robotium
## What it can do
    Robotium是一款国外的Android自动化测试框架，主要针对Android平台的应用进行黑盒自动化测试，它提供了模拟各种手势操作（点击、长按、滑动等）、查找和断言机制的API，能够对各种控件进行操作。Robotium结合Android官方提供的测试框架达到对应用程序进行自动化的测试。

## How to use 
### white box testing
1. Download "robotium-solo.jar"
2. Adds library
3. Create test class in androidTest package
4. Run your test class
```sh
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
}
```
### [black box testing](http://www.51testing.com/html/47/411547-812831.html)



## What are pros and cons

### The Good:
- Easy to use
- Tests are easy to read
- Doesn't require access to source code. Can test a APK
- Can identify elements easily (with caveat...more on this later)
- Can fall back on default Android framework
- Great support

### The Bad:
- Not all views and objects are currently supported e.g. SlidingDrawer
- Slower compared to unit testing
- Single class containing all methods, Selenium 1 style. This is going to get messy


## References
[Robotium in Android Studio](http://anirudh24seven.github.io/devlog/2015/02/13/robotium-android-studio.html)

[Robotium API](http://robotium.googlecode.com/svn/doc/index.html)

[User Guide Android Studio](http://robotium.com/pages/user-guide-android-studio)

[ANDROID自动化测试 robotium](http://www.51testing.com/html/26/492926-842908.html)
