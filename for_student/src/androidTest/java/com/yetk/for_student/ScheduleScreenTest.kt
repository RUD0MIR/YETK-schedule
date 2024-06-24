package com.yetk.for_student

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.screen.ScheduleScreen
import org.junit.Rule
import org.junit.Test
import com.yetk.model.Response
import org.junit.Before

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
        rule.onNodeWithText(TestData.collegeGroup.relevantFromTo).assertExists()
    }

    @Test
    fun groupNameInformationDisplayed() {
        rule.onNodeWithText(TestData.collegeGroup.name).assertExists()
    }

    @Test
    fun weekStateInformationDisplayed() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val lowerWeekTitle = context.resources.getString(R.string.lower_week)

        rule.onNodeWithText(lowerWeekTitle).assertExists()
    }

    //weekStatePreview toggle tests
    @Test
    fun weekStatePreviewToggleDisplayingLowerWeek() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton)).assertExists()
    }

    @Test
    fun weekStatePreviewToggleDisplayingUpperWeek() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_lower_week_action)).assertExists()
    }

    //list items tests
    @Test
    fun lesson1_OnMondayOnLowerWeekIsNotDisplayed() {
        /**
         from class TestData:
        Lesson(
            "Английский язык",
            rooms = listOf("304"),
            teachers = listOf("Колобова Р.В.", "Анна Н.Н."),
            number = 1,
            dayOfWeek = 0,
            weekState = WeekState.UpperWeek.id
        )
        */
        rule.onNodeWithText(TestData.collegeGroup.lessons[0].subject).assertDoesNotExist()
    }

    @Test
    fun lesson2_OnMondayOnLowerWeekExists() {
        /**
        from class TestData:
        Lesson(
            "Математика",
            rooms = listOf("208м"),
            teachers = listOf("Иванова Б.В."),
            number = 2,
            dayOfWeek = 0,
            weekState = WeekState.LowerWeek.id
        )
        */
        rule.onNodeWithText(TestData.collegeGroup.lessons[1].subject).assertExists()
    }

    @Test
    fun lesson1_OnMondayOnUpperWeekExists() {
        /**
         * from class TestData:
        Lesson(
            "Английский язык",
            rooms = listOf("304"),
            teachers = listOf("Колобова Р.В.", "Анна Н.Н."),
            number = 1,
            dayOfWeek = 0,
            weekState = WeekState.UpperWeek.id
        )
        */
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()
        rule.onNodeWithText(TestData.collegeGroup.lessons[0].subject).assertExists()
    }

    @Test
    fun lesson3_OnTuesdayOnUpperWeekExists() {
        /**
         * from class TestData:
        Lesson(
            "Физкультура",
            rooms = listOf("102"),
            teachers = listOf("Георгиев Ж.В."),
            number = 3,
            dayOfWeek = 1,
            weekState = WeekState.UpperWeek.id
        )
        */
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(context.resources.getStringArray(R.array.week_days)[1]).performClick()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()
        rule.onNodeWithText(TestData.collegeGroup.lessons[2].subject).assertExists()
    }

    @Test
    fun lesson4_OnTuesdayOnLowerWeekDoesNotExists() {
        /**
         * from class TestData:
        Lesson(
            "Информатика",
            rooms = listOf("103"),
            teachers = listOf("Александра Г.Г."),
            number = 4,
            dayOfWeek = 1,
            weekState = WeekState.UpperWeek.id
        )
        */
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(context.resources.getStringArray(R.array.week_days)[1]).performClick()
        rule.onNodeWithText(TestData.collegeGroup.lessons[3].subject).assertDoesNotExist()
    }

    @Test
    fun lesson5_OnWednesdayOnLowerWeekExists() {
        /**
         * from class TestData:
        Lesson(
        "Физика",
        rooms = listOf("404"),
        teachers = listOf("Петров Г.Г."),
        number = 5,
        dayOfWeek = 2,
        weekState = WeekState.EveryWeek.id
        )
         */
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(context.resources.getStringArray(R.array.week_days)[2]).performClick()
        rule.onNodeWithText(TestData.collegeGroup.lessons[4].subject).assertExists()
    }

    @Test
    fun lesson5_OnWednesdayOnUpperWeekExists() {
        /**
         * from class TestData:
        Lesson(
        "Физика",
        rooms = listOf("404"),
        teachers = listOf("Петров Г.Г."),
        number = 5,
        dayOfWeek = 2,
        weekState = WeekState.EveryWeek.id
        )
         */
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(context.resources.getStringArray(R.array.week_days)[2]).performClick()

        rule.onNodeWithContentDescription(context.resources.getString(R.string.to_upper_week_aciton))
            .performClick()

        rule.onNodeWithText(TestData.collegeGroup.lessons[4].subject).assertExists()
    }
}