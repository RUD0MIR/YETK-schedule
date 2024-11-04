package com.yetk.forstudent.ui

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
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.forstudent.R
import com.yetk.forstudent.TestData
import com.yetk.forstudent.common.DetailScreenType
import com.yetk.forstudent.domain.model.Homework
import com.yetk.forstudent.ui.screen.homework.HomeworkDetailScreen
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
            homeworks =
                remember {
                    SnapshotStateList()
                }

            navigateUpInvoked =
                remember {
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

    @Test
    fun autoCompleteTextInputTest() {
        val text = "some text"
        rule.onNodeWithText(context.getString(R.string.subject))
            .performTextInput(text)

        rule.onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun homeworkTextFieldTextInputTest() {
        val text = "other text"
        rule.onNodeWithText(context.getString(R.string.homework))
            .performTextInput(text)

        rule.onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun positiveButtonTest() {
        val subjectName = "Math"
        val content = "some content"

        rule.onNodeWithText(context.getString(R.string.add_action))
            .assertIsDisplayed()

        rule.onNodeWithText(context.getString(R.string.subject))
            .performTextInput(subjectName)

        rule.onNodeWithText(context.getString(R.string.homework))
            .performTextInput(content)

        rule.onNodeWithText(context.getString(R.string.add_action))
            .performClick()

        val insertedHomework = homeworks.last()

        Assert.assertTrue(
            "Homework insert is not invoked",
            insertedHomework.subjectName == subjectName && insertedHomework.content == content
        )
    }

    @Test
    fun negativeButtonTest() {
        rule.onNodeWithText(context.getString(R.string.cancel_action))
            .assertIsDisplayed()
            .performClick()

        Assert.assertTrue(
            "Negative button callback is not invoked",
            navigateUpInvoked.value
        )
    }
}
