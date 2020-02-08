package com.sphtech.mobiledatausage

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith

import com.sphtech.mobiledatausage.ui.activities.MainActivity
import com.sphtech.mobiledatausage.ui.adapters.DataUsageAdapter
import org.junit.Rule
import android.content.Intent




@RunWith(AndroidJUnit4::class)
class ActivityTest {

    @Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickOnItem() {

        mActivityRule.launchActivity(Intent())

        onView(withId(R.id.rvList))
            .perform(waitFor(10000))
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {

            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return isRoot()
            }

            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
                actionOnItemAtPosition<DataUsageAdapter.MyViewHolder>(1000, click())
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }
        }
    }

}
