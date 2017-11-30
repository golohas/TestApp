package com.example.mcf.testapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.robolectric.Shadows.shadowOf;


/**
 * Created by Administrator on 2017/11/29.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void testLifecycle() {
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = controller.get();
        //assertNull(activity.getLifecycle().getCurrentState());

        controller.create();
        assertEquals("CREATED", activity.getLifecycle().getCurrentState().toString());

        controller.start();
        assertEquals("STARTED", activity.getLifecycle().getCurrentState().toString());

        controller.resume();
        assertEquals("RESUMED",activity.getLifecycle().getCurrentState().toString());
    }
    @Test
    public void testJump() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.Login).performClick();

        ShadowActivity shadowActivity = shadowOf(activity);
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        assertEquals(nextIntent.getComponent().getClassName(), LoginActivity.class.getName());
    }

    @Test
    public void testToast() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Toast toast = ShadowToast.getLatestToast();
        assertNull(toast);

        activity.findViewById(R.id.Login).performClick();
        toast = ShadowToast.getLatestToast();
        assertNotNull(toast);
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals(Toast.LENGTH_LONG, shadowToast.getDuration());
        assertEquals("test toast", shadowToast.getTextOfLatestToast());
    }

    @Test
    public void testTextView() {


        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Button changeBtn = activity.findViewById(R.id.showView);
        TextView resultTV = activity.findViewById(R.id.result);

        String expectedStr = activity.getResources().getString(R.string.resultStr);
        changeBtn.performClick();
        //assertThat(resultTV.getText().toString(), equalTo("This is a TextView"));
        assertThat(resultTV.getText().toString(), equalTo(expectedStr));
    }
}
