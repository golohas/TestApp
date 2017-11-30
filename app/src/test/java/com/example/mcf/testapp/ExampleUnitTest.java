package com.example.mcf.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import org.apache.tools.ant.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExampleUnitTest {

    @Test
    public void testLoginActivity() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.Login).performClick();
        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
    @Test
//    @Config(qualifiers = "en-zh")
    public void testResource(){
        final Context context = RuntimeEnvironment.application;
        assertThat(context.getString(R.string.Login), is("Login"));
    }

    @Test
    public void testLifeCycle() throws Exception{
        ActivityController controller = Robolectric.buildActivity(MainActivity.class).create().start().resume();
        Activity activity = (Activity) controller.get();
//        String text  = (String) activity.getApplicationContext().getText(R.id.Login);
        Button btn = (Button) activity.findViewById(R.id.Login);
        System.out.println(btn.getText());
//        System.out.println(text);
//        assertNotNull(activity.getTitle());
        assertEquals(activity.getTitle(), "TestApp");
        //System.out.println(activity.getTitle());
    }
}