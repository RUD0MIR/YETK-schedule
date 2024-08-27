package com.yetk.for_student.ui

import android.content.Context
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.onSiblings
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.for_student.TestData
import com.yetk.for_student.common.Response
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

        subjects.forEach {
            rule.onNodeWithText(it.name).onSibling()
                .assert(hasContentDescription(context.getString(R.string.expand_action)))
                .assertHasClickAction()
                .assertIsDisplayed()
        }

        subjects.forEach {
            rule.onNodeWithText(it.name).onSibling().performClick()
        }

        subjects.forEach {
            rule.onNodeWithText(it.name).onSiblings().onLast()
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

        subjects.forEach {
            rule.onNodeWithText(it.name).onSibling().performClick()
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

        subjects.forEach {
            rule.onNodeWithText(it.name).onSibling().performClick()
        }

        subjects.forEach { subject ->
            subject.teachers.forEach { teacher ->
                rule.onNodeWithText(teacher).assertIsDisplayed()
            }
        }
    }
}