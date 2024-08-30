package com.yetk.for_student.ui

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.yetk.for_student.common.DetailScreenType
import com.yetk.for_student.domain.model.Homework
import com.yetk.for_student.ui.screen.homework.HomeworkDetailScreen
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeworkAddScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private lateinit var context: Context

    private lateinit var homeworks: SnapshotStateList<Homework>
    private lateinit var navigateUpInvoked: MutableState<Boolean>

    private val subjectsNames = TestData.subjectsNames

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.setContent {
            homeworks = remember {
                SnapshotStateList()
            }

            navigateUpInvoked = remember {
                mutableStateOf(false)
            }


            YetkScheduleTheme {
                HomeworkDetailScreen(
                    homeworkSubject = "",
                    homeworkContent = "",
                    detailScreenType = DetailScreenType.AddScreen,
                    subjectsNames = subjectsNames,
                    checkCorrectInput = { s: String, s1: String -> true },
                    onNavigateUp = { navigateUpInvoked.value = true },
                    onHomeworkCheck = {},
                    onHomeworkDelete = {},
                    onHomeworkInsert = { homeworks.add(it) },
                    onHomeworkUpdate = {}
                )
            }
        }
    }

    @Test
    fun navigateUpButtonWorks() {
        rule.onNodeWithContentDescription(context.getString(R.string.navigate_back))
            .assertIsDisplayed()
            .performClick()

        Assert.assertTrue("Navigation action is not invoked!", navigateUpInvoked.value)
    }

    @Test
    fun autoCompleteResizingCorrectly() {
        subjectsNames.forEach {
            rule.onNodeWithText(it).assertIsNotDisplayed()
        }

        rule.onNodeWithContentDescription(context.getString(R.string.expand_action))
            .assertIsDisplayed()
            .performClick()

        subjectsNames.forEach {
            rule.onNodeWithText(it).assertIsDisplayed()
        }

        rule.onNodeWithContentDescription(context.getString(R.string.shrink_action))
            .assertIsDisplayed()
            .performClick()

        subjectsNames.forEach {
            rule.onNodeWithText(it).assertIsNotDisplayed()
        }
    }

    @Test
    fun autoCompleteChosenOptionDisplayed() {
        val chosenSubject = subjectsNames.first()
        rule.onNodeWithContentDescription(context.getString(R.string.expand_action))
            .assertIsDisplayed()
            .performClick()


        rule.onNodeWithText(chosenSubject)
            .assertIsDisplayed()
            .performClick()

        subjectsNames.filter {
            it != chosenSubject
        }.forEach {
            rule.onNodeWithText(it).assertIsNotDisplayed()
        }

        rule.onNodeWithText(chosenSubject)
            .assertIsDisplayed()
    }
}