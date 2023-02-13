package com.github.ihermandev.sample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup
import org.junit.Rule
import org.junit.Test

internal class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkRadioButtonRegularCase() {

        activityRule.scenario.onActivity { activity ->
            val radioGroup = activity.findViewById<AdvancedRadioGroup>(R.id.numberRadioGroup)
            radioGroup.checkId(R.id.oneRadioButton)
        }

        onView(withId(R.id.oneRadioButton)).check(matches(isChecked()))
    }

    @Test
    fun checkRadioButtonInsideConstraintLayoutContainerCase() {

        activityRule.scenario.onActivity { activity ->
            val radioGroup = activity.findViewById<AdvancedRadioGroup>(R.id.titleRadioGroup)
            radioGroup.checkId(R.id.secondTitleRadioButton)
        }

        onView(withId(R.id.secondTitleRadioButton)).check(matches(isChecked()))
    }

    @Test
    fun checkRadioButtonInsideLinearLayoutContainerCase() {

        activityRule.scenario.onActivity { activity ->
            val radioGroup = activity.findViewById<AdvancedRadioGroup>(R.id.assetRadioGroup)
            radioGroup.checkId(R.id.twoKRadioButton)
        }

        onView(withId(R.id.twoKRadioButton)).check(matches(isChecked()))
    }
}