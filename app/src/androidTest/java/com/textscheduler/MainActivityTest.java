package com.textscheduler;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertNotNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.textscheduler.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) // Only needed when mixing JUnit 3 and 4 tests
public class MainActivityTest {

    private static final String stringToBeTyped = "Espresso";

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void buttonClick_navigatesToPage() {
        // First, check that the buttons exist
        assertNotNull(onView(withId(R.id.button_new)));
        assertNotNull(onView(withId(R.id.button_existing)));

        // When the New Text button is clicked, the New Text screen is displayed
        onView(withId(R.id.button_new)).perform(click());
        onView(withId(R.id.button_submit)).check(matches(isDisplayed()));

        // When the Existing Texts button is clicked, the Existing Texts screen is displayed
        onView(withId(R.id.button_existing)).perform(click());
        onView(withId(R.id.sms_list)).check(matches(isDisplayed()));
    }
}