package com.yetk.forstudent.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.forstudent.TestData
import com.yetk.forstudent.common.Response
import com.yetk.forstudent.common.parseNhNmin
import com.yetk.forstudent.ui.screen.bellschedule.BellScheduleScreen
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
