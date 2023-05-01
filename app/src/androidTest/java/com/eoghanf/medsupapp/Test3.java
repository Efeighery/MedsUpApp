package com.example.medsupapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.medsupapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test3 {

    private static final String testString = "User7";

    @Rule
    public ActivityTestRule<Login> activityTestRule = new ActivityTestRule<>(Login.class);

    @Test
    public void alterTextField_sameActivity(){
        onView(ViewMatchers.withId(R.id.userEmail))
                .perform(typeText(testString));

        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.userEmail))
                .check(matches(withText(testString)));
    }
}
