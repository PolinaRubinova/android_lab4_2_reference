package com.example.myapplication

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import android.content.pm.ActivityInfo
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.Assert.assertTrue
import org.junit.Rule
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private fun changeScreenOrientation(mode: Boolean) {
        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation =
                if (mode) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
            sleep(1000)
    }

    private fun fr1exist() {
        onView(withId(R.id.fragment1)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
        onView(withId(R.id.bnToFirst)).check(doesNotExist())
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
//        onView(withId(R.id.text1)).check(isCompletelyAbove(withId(R.id.bnToSecond)))
//        onView(withId(R.id.bnToSecond)).check(isCompletelyAbove(withId(R.id.nav_view)))
    }

    private fun fr2exist() {
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment3)).check(doesNotExist())
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(doesNotExist())
        onView(withId(R.id.bnToThird)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
//        onView(withId(R.id.text2)).check(isCompletelyAbove(withId(R.id.bnToFirst)))
//        onView(withId(R.id.bnToFirst)).check(isCompletelyAbove(withId(R.id.bnToThird)))
//        onView(withId(R.id.bnToThird)).check(isCompletelyAbove(withId(R.id.nav_view)))
    }

    private fun fr3exist() {
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
//        onView(withId(R.id.text3)).check(isCompletelyAbove(withId(R.id.bnToFirst)))
//        onView(withId(R.id.bnToFirst)).check(isCompletelyAbove(withId(R.id.bnToSecond)))
//        onView(withId(R.id.bnToSecond)).check(isCompletelyAbove(withId(R.id.nav_view)))
    }

    private fun aboutExist() {
        onView(withId(R.id.activity_about)).check(matches(isDisplayed()))
        onView(withId(R.id.tvAbout)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToFirst)).check(doesNotExist())
        onView(withId(R.id.bnToSecond)).check(doesNotExist())
        onView(withId(R.id.bnToThird)).check(doesNotExist())
    }

    @Test
    fun testAbout() {
        launchActivity<MainActivity>()
        openAbout()
        Espresso.onView(ViewMatchers.withId(R.id.activity_about))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun existenceOfElementsTestFr1() {
        fr1exist()
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
    }

    @Test
    fun existenceOfElementsTestFr2() {
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
    }

    @Test
    fun existenceOfElementsTestFr3() {
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(doesNotExist())
    }

    @Test
    fun existenceOfElementsTestAbout() {
        openAbout()
        aboutExist()
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
    }

    @Test
    fun backstackDepthTest() {
        fr1exist()
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()

        onView(withId(R.id.bnToFirst)).perform(click())
        fr1exist()
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()

        onView(withId(R.id.bnToFirst)).perform(click())
        fr1exist()
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()

        openAbout()
        aboutExist()

        pressBack()

        fr3exist()
        pressBack()
        fr2exist()
        pressBack()
        fr1exist()
    }

    @Test
    fun navigationForwardTest() {
        fr1exist()
        openAbout()
        aboutExist()
        pressBack()

        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()

        openAbout()
        aboutExist()
        pressBack()

        onView(withId(R.id.bnToFirst)).perform(click())
        fr1exist()
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()

        openAbout()
        aboutExist()
        pressBack()

        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()

        openAbout()
        aboutExist()
        pressBack()

        onView(withId(R.id.bnToFirst)).perform(click())
        fr1exist()
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())

        fr3exist()
        pressBack()
        fr2exist()
        pressBack()
        fr1exist()
    }

    @Test
    fun navigationUpTest() {
        fr1exist()
        openAbout()
        aboutExist()
        onView(withContentDescription("Navigate up")).perform(click());
        //onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())

        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()

        openAbout()
        aboutExist()
        onView(withContentDescription("Navigate up")).perform(click());

        onView(withId(R.id.bnToFirst)).perform(click())
        fr1exist()
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()

        openAbout()
        aboutExist()
        onView(withContentDescription("Navigate up")).perform(click());

        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()

        openAbout()
        aboutExist()
        onView(withContentDescription("Navigate up")).perform(click());

        onView(withId(R.id.bnToFirst)).perform(click())
        fr1exist()
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        onView(withId(R.id.bnToThird)).perform(click())

        fr3exist()
        onView(withContentDescription("Navigate up")).perform(click());
        fr2exist()
        onView(withContentDescription("Navigate up")).perform(click());
        fr1exist()
    }

    @Test
    fun changeScreenOrientationTestFr1() {
        fr1exist()
        changeScreenOrientation(true)
        fr1exist()
        openAbout()
        aboutExist()
        pressBack()
        changeScreenOrientation(false)
        fr1exist()
    }

    @Test
    fun changeScreenOrientationTestFr2() {
        onView(withId(R.id.bnToSecond)).perform(click())
        fr2exist()
        changeScreenOrientation(true)
        fr2exist()
        openAbout()
        aboutExist()
        pressBack()
        changeScreenOrientation(false)
        fr2exist()
    }

    @Test
    fun changeScreenOrientationTestFr3() {
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        fr3exist()
        changeScreenOrientation(true)
        fr3exist()
        openAbout()
        aboutExist()
        pressBack()
        changeScreenOrientation(false)
        fr3exist()
    }

    @Test
    fun changeScreenOrientationTestAbout() {
        openAbout()
        aboutExist()
        changeScreenOrientation(true)
        aboutExist()
        changeScreenOrientation(false)
        aboutExist()
    }
}