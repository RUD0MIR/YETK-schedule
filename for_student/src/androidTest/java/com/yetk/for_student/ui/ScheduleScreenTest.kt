package com.yetk.for_student.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.for_student.TestData
import com.yetk.for_student.common.Response
import com.yetk.for_student.ui.screen.schedule.ScheduleScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScheduleScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setUp() {
        rule.setContent {
            YetkScheduleTheme {
                ScheduleScreen(
                    collegeGroup = Response.Success(TestData.collegeGroup),
                    isLowerWeek = Response.Success(true),
                    bellSchedule = Response.Success(TestData.bellSchedule)
                )
            }
        }
    }

    //data section tests
    @Test
    fun relevantFromToInformationDisplayed() {
        rule.onNodeWithText(TestData.collegeGroup.relevantFromTo).assertIsDisplayed()
    }

    @Test
    fun groupNameInformationDisplayed() {
        rule.onNodeWithText(TestData.collegeGroup.name).assertIsDisplayed()
    }

    @Test
    fun weekStateInformationDisplayed() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val lowerWeekTitle = context.resources.getString(R.string.lower_week)

        rule.onNodeWithText(lowerWeekTitle).assertIsDisplayed()
    }

    @Test
    fun weekStatePreviewToggleSwitchesCorrectly() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_lower_week_action))
            .assertIsDisplayed()
    }

    //list items tests
    @Test
    fun lesson1DisplayedCorrectly() {
        val lesson1 = TestData.collegeGroup.lessons[0]
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(lesson1.subject).assertIsDisplayed()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()

        rule.onNodeWithText(lesson1.subject).assertIsDisplayed()

    }

    @Test
    fun lesson2DisplayedCorrectly() {
        val lesson2 = TestData.collegeGroup.lessons[1]
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(lesson2.subject).assertIsNotDisplayed()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()

        rule.onNodeWithText(lesson2.subject).assertIsDisplayed()
    }

    @Test
    fun lesson3DisplayedCorrectly() {
        val lesson3 = TestData.collegeGroup.lessons[2]
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(lesson3.subject).assertIsNotDisplayed()

        rule.onNodeWithText(context.resources.getStringArray(R.array.week_days)[1]).performClick()

        rule.onNodeWithText(lesson3.subject).assertIsDisplayed()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()

        rule.onNodeWithText(lesson3.subject).assertIsNotDisplayed()
    }
}