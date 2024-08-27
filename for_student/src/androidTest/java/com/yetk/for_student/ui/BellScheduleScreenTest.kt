package com.yetk.for_student.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.TestData
import com.yetk.for_student.common.Response
import com.yetk.for_student.common.parseNhNmin
import com.yetk.for_student.ui.screen.bell_schedule.BellScheduleScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BellScheduleScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setUp() {
        rule.setContent {
            YetkScheduleTheme {
                BellScheduleScreen(
                    bellSchedule = Response.Success(TestData.bellSchedule)
                )
            }
        }
    }

    @Test
    fun bellScheduleDataDisplayed() {
        val data = TestData.bellSchedule
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.onNodeWithText(data.lessonDurationMin.parseNhNmin(context)).assertIsDisplayed()

        data.lessonsTime.forEach {
            rule.onNodeWithText(it).assertIsDisplayed()
        }
    }
}