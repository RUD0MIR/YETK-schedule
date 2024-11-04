package com.yetk.forstudent.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.forstudent.R
import com.yetk.forstudent.TestData
import com.yetk.forstudent.WeekState
import com.yetk.forstudent.common.Response
import com.yetk.forstudent.ui.screen.schedule.ScheduleScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScheduleScreenTest {
    @get:Rule
    val rule = createComposeRule()

    private lateinit var context: Context
    private lateinit var upperWeekAction: String
    private lateinit var lowerWeekAction: String
    private lateinit var weekDays: Array<String>

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        upperWeekAction = context.resources.getString(R.string.to_upper_week_aciton)
        lowerWeekAction = context.resources.getString(R.string.to_lower_week_action)
        weekDays = context.resources.getStringArray(R.array.week_days)

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

    // data section tests
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
        val lowerWeekTitle = context.resources.getString(R.string.lower_week)
        rule.onNodeWithText(lowerWeekTitle).assertIsDisplayed()
    }

    @Test
    fun weekStatePreviewToggleSwitchesCorrectly() {
        rule.onNodeWithContentDescription(upperWeekAction)
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(upperWeekAction)
            .performClick()

        rule.onNodeWithContentDescription(lowerWeekAction)
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(lowerWeekAction)
            .performClick()

        rule.onNodeWithContentDescription(upperWeekAction)
            .assertIsDisplayed()
    }

    // list items tests
    @Test
    fun lessonsDisplayedCorrectly() {
        val lessons = TestData.collegeGroup.lessons

        rule.onNodeWithContentDescription(upperWeekAction).assertIsDisplayed()

        val mondayUpperWeekLessons =
            lessons.filter {
                it.dayOfWeek == 0 && it.weekState == WeekState.UpperWeek.id
            }
        val mondayEveryWeekLessons =
            lessons.filter {
                it.dayOfWeek == 0 && it.weekState == WeekState.EveryWeek.id
            }
        val mondayLowerWeekLessons =
            lessons.filter {
                it.dayOfWeek == 0 && it.weekState == WeekState.LowerWeek.id
            }

        mondayLowerWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        mondayEveryWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        mondayUpperWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsNotDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsNotDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsNotDisplayed()
        }

        rule.onNodeWithContentDescription(upperWeekAction)
            .performClick()

        mondayLowerWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsNotDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsNotDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsNotDisplayed()
        }

        mondayEveryWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        mondayUpperWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        rule.onNodeWithText(weekDays[1]).performClick()

        val tuesdayUpperWeekLessons =
            lessons.filter {
                it.dayOfWeek == 1 && it.weekState == WeekState.UpperWeek.id
            }
        val tuesdayEveryWeekLessons =
            lessons.filter {
                it.dayOfWeek == 1 && it.weekState == WeekState.EveryWeek.id
            }
        val tuesdayLowerWeekLessons =
            lessons.filter {
                it.dayOfWeek == 1 && it.weekState == WeekState.LowerWeek.id
            }

        tuesdayLowerWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsNotDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsNotDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsNotDisplayed()
        }

        tuesdayEveryWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        tuesdayUpperWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        rule.onNodeWithContentDescription(
            context.resources.getString(R.string.to_lower_week_action)
        )
            .performClick()

        tuesdayLowerWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        tuesdayEveryWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsDisplayed()
        }

        tuesdayUpperWeekLessons.forEach {
            rule.onNodeWithText(it.subject).assertIsNotDisplayed()
            rule.onNodeWithText(it.rooms[0]).assertIsNotDisplayed()
            rule.onNodeWithText(it.teachers[0]).assertIsNotDisplayed()
        }
    }
}
