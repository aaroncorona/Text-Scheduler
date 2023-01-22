package com.textscheduler;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.textscheduler.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) // Only needed when mixing JUnit 3 and 4 tests
public class NewTextFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void whenTextInputAdded_inputExists() {
        // First, navigate to New Text screen
        onView(withId(R.id.button_new)).perform(click());
        // Check that SMS input boxes can be filled properly
        onView(withId(R.id.text_date_input)).perform(typeText("06/10/23"));
        onView(withId(R.id.text_date_input)).check(matches(withText("06/10/23")));
        onView(withId(R.id.text_time_input)).perform(typeText("08:00"));
        onView(withId(R.id.text_time_input)).check(matches(withText("08:00")));
        onView(withId(R.id.text_phone_input)).perform(typeText("3108675309"));
        onView(withId(R.id.text_phone_input)).check(matches(withText("3108675309")));
        onView(withId(R.id.text_message_input)).perform(typeText("Happy Birthday!"));
        onView(withId(R.id.text_message_input)).check(matches(withText("Happy Birthday!")));
    }
}