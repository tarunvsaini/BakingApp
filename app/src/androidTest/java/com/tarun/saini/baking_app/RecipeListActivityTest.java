package com.tarun.saini.baking_app;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.tarun.saini.baking_app.ui.RecipeListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipeListActivity.class);

  /*  private IdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource()
    {
        Espresso.registerIdlingResources(mIdlingResource);
    }*/

    @Test
    public void clickRecipeRecyclerView() {


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_recyclerView), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        onView(withId(R.id.recipeName)).check(matches(withText("Cheesecake")));

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.addWidgetBox),
                        withParent(allOf(withId(R.id.recipeName_linerLayout),
                                withParent(withId(R.id.backdrop_linerLayout)))),
                        isDisplayed()));
        appCompatCheckBox.perform(click());


        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());
        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.show_steps), withText("SHOW STEPS"),
                        withParent(allOf(withId(R.id.nestedLinearLayout),
                                withParent(withId(R.id.nestedScrollView)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.step_list_recyclerView),
                        withParent(withId(R.id.fragment2)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*ViewInteraction appCompatImageButton = onView(
                withId(R.id.next_button));
        appCompatImageButton.perform(scrollTo(), click());*/

    }

}
