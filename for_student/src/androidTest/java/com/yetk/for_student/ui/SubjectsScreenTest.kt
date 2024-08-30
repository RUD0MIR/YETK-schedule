package com.yetk.for_student.ui

import android.content.Context
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.for_student.TestData
import com.yetk.for_student.common.Response
import com.yetk.for_student.common.Tags
import com.yetk.for_student.ui.screen.subjects.SubjectsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SubjectsScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setUp() {
        rule.setContent {
            YetkScheduleTheme {
                SubjectsScreen(collegeGroupData = Response.Success(TestData.collegeGroup))
            }
        }
    }

    @Test
    fun expandToggleDisplayedCorrectly() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val subjects = TestData.collegeGroup.subjects

        subjects.forEachIndexed { i, _ ->
            rule.onNodeWithTag(Tags.ExpandToggle(i).tag)
                .assert(hasContentDescription(context.getString(R.string.expand_action)))
                .assertHasClickAction()
                .assertIsDisplayed()
        }

        subjects.forEachIndexed { i, _ ->
            rule.onNodeWithTag(Tags.ExpandToggle(i).tag).performClick()
        }

        subjects.forEachIndexed { i, _ ->
            rule.onNodeWithTag(Tags.ExpandToggle(i).tag)
                .assert(hasContentDescription(context.getString(R.string.shrink_action)))
                .assertHasClickAction()
                .assertIsDisplayed()
        }
    }

    @Test
    fun subjectsDisplayedCorrectly() {
        val subjects = TestData.collegeGroup.subjects

        subjects.forEach {
            rule.onNodeWithText(it.name).assertIsDisplayed()
        }

        subjects.forEachIndexed { i, _ ->
            rule.onNodeWithTag(Tags.ExpandToggle(i).tag).performClick()
        }

        subjects.forEach {
            rule.onNodeWithText(it.name).assertIsDisplayed()
        }
    }

    @Test
    fun teachersDisplayedCorrectly() {
        val subjects = TestData.collegeGroup.subjects

        subjects.forEach { subject ->
            subject.teachers.forEach { teacher ->
                rule.onNodeWithText(teacher).assertIsNotDisplayed()
            }
        }

        subjects.forEachIndexed { i, _ ->
            rule.onNodeWithTag(Tags.ExpandToggle(i).tag).performClick()
        }

        subjects.forEach { subject ->
            subject.teachers.forEach { teacher ->
                rule.onNodeWithText(teacher).assertIsDisplayed()
            }
        }
    }
}