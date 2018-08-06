package com.example.fcl.kotlindemo;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.fcl.kotlindemo.test.WebActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by galio.fang on 18-8-6
 */
@RunWith(AndroidJUnit4.class)
public class WebTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(WebActivity.class,false);

    @Test
    public void testweb() {
        Intent intent = new Intent();
        intent.putExtra("extra_url", "http://www.baidu.com");
        activityTestRule.launchActivity(intent);
    }

}
