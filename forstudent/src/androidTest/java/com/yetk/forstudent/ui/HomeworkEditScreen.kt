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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.forstudent.R
import com.yetk.forstudent.TestData
import com.yetk.forstudent.common.DetailScreenType
import com.yetk.forstudent.common.Tags
import com.yetk.forstudent.domain.model.Homework
import com.yetk.forstudent.ui.screen.homework.HomeworkDetailScreen
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeworkEditScreen {
    @get:Rule
    val rule = createComposeRule()

    private lateinit var context: Context

    private lateinit var homeworks: SnapshotStateList<Homework>
    private lateinit var navigateUpInvoked: MutableState<Boolean>
    private val itemId = 0
    private lateinit var currentHomework: Homework

    private val subjectsNames = TestData.subjectsNames

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        rule.setContent {
            homeworks =
                remember {
                    SnapshotStateList()
                }

            homeworks.addAll(TestData.homeworks)

            currentHomework = homeworks[itemId]

            navigateUpInvoked =
                remember {
                    mutableStateOf(false)
                }

            YetkScheduleTheme {
                HomeworkDetailScreen(
                    homeworkSubject = homeworks[itemId].subjectName,
                    homeworkContent = homeworks[itemId].content,
                    detailScreenType = DetailScreenType.EditScreen(itemId),
                    subjectsNames = subjectsNames,
                    checkCorrectInput = { s: String, s1: String -> true },
                    onNavigateUp = { navigateUpInvoked.value = true },
                    onHomeworkCheck = { homeworks.removeAt(it) },
                    onHomeworkDelete = { homeworks.removeAt(it) },
                    onHomeworkInsert = {},
                    onHomeworkUpdate = { homeworks[it.id] = it }
                )
            }
        }
    }

    @Test
    fun checkBoxTest() {
        val currentHomework = homeworks[itemId]

        rule.onNodeWithTag(Tags.CheckBox(itemId).tag)
            .assertIsDisplayed()
            .performClick()

        Assert.assertTrue(currentHomework !in homeworks)
    }

    @Test
    fun deleteActionTest() {
        val currentHomework = homeworks[itemId]

        rule.onNodeWithContentDescription(context.getString(R.string.delete_homework_action))
            .assertIsDisplayed()
            .performClick()

        Assert.assertTrue(currentHomework !in homeworks)
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

        rule.onNodeWithText(currentHomework.subjectName)
            .performTextClearance()

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

        rule.onNodeWithText(currentHomework.subjectName)
            .performTextClearance()

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

        rule.onNodeWithText(currentHomework.subjectName)
            .performTextClearance()

        rule.onNodeWithText(context.getString(R.string.subject))
            .performTextInput(text)

        rule.onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun homeworkTextFieldTextInputTest() {
        val text = "other text"

        rule.onNodeWithText(currentHomework.content)
            .performTextClearance()

        rule.onNodeWithText(context.getString(R.string.homework))
            .performTextInput(text)

        rule.onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun positiveButtonTest() {
        val subjectName = "new name"
        val content = "new content"

        rule.onNodeWithText(context.getString(R.string.save_action))
            .assertIsDisplayed()

        rule.onNodeWithText(homeworks[itemId].subjectName)
            .performTextReplacement(subjectName)

        rule.onNodeWithText(homeworks[itemId].content)
            .performTextReplacement(content)

        rule.onNodeWithText(context.getString(R.string.save_action))
            .performClick()

        val updatedHomework = homeworks[itemId]

        Assert.assertTrue(
            "Homework is not updated",
            updatedHomework.subjectName == subjectName && updatedHomework.content == content
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
