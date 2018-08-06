package com.example.fcl.kotlindemo;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.fcl.kotlindemo.test.EspressActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by galio.fang on 18-8-6
 */
@RunWith(AndroidJUnit4.class)
public class EsTest {

    @Rule
    public ActivityTestRule activityTestRule=new ActivityTestRule(EspressActivity.class);

    @Test
    public void test() {
        //onView(withId(R.id.edit)).perform(typeText("Peter"), closeSoftKeyboard());
        //onView(withText("say hello")).perform(click());
        //
        //onView(withId(R.id.text)).check(matches(withText("hello, Peter")));

        onView(withText("recycler")).perform(click());
        onView(withText("Item 0")).check(matches(isDisplayed())).perform(click());
        onView(withText("确定")).perform(click());
        onView(withText("Item 2")).check(matches(isDisplayed())).perform(click());
        pressBack();
    }

    @Test
    public void testRecycler() {
        onView(withText("recycler")).perform(click());
        onView(withText("Item 0")).check(matches(isDisplayed())).perform(click());
        onView(withText("确定")).perform(click());
        onView(withText("Item 2")).check(matches(isDisplayed())).perform(click());
        pressBack();
    }

    @Test
    public void testWeb() {

    }
}
