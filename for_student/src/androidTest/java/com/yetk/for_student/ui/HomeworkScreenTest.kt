package com.yetk.for_student.ui

import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.TestData
import com.yetk.for_student.common.Tags
import com.yetk.for_student.domain.model.Homework
import com.yetk.for_student.ui.screen.homework.HomeworkScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeworkScreenTest {
    @get:Rule
    val rule = createComposeRule()

    private lateinit var homeworks: SnapshotStateList<Homework>

    @Before
    fun setUp() {
        rule.setContent {
            homeworks = remember {
                SnapshotStateList()
            }

            homeworks.addAll(TestData.homeworks)

            YetkScheduleTheme {
                HomeworkScreen(
                    homeworks = homeworks,
                    checkBoxAnimDuration = 0L,
                    onNavigateToEditScreen = { _, _, _ -> },
                    onHomeworkCheck = {
                        homeworks.removeAt(it)
                    },
                    onHomeworkDelete = {
                        homeworks.removeAt(it)
                    },
                    onNavigateToAddScreen = {},
                    onShowSnackbar = { _, _ -> false }
                )
            }
        }
    }

    @Test
    fun homeworkItemsDisplayedCorrectly() {
        homeworks.forEach {
            rule.onNodeWithText(it.content).assertIsDisplayed()
            rule.onNodeWithText(it.subjectName).assertIsDisplayed()

            rule.onNodeWithTag(Tags.CheckBox(it.id).tag)
                .assertHasClickAction()
                .assertIsDisplayed()
        }
    }

    @Test
    fun checkBoxesWorks() {
        homeworks.forEach {
            rule.onNodeWithText(it.content).assertIsDisplayed()
            rule.onNodeWithText(it.subjectName).assertIsDisplayed()
        }

        homeworks.reversed().forEach {
            rule.onNodeWithTag(Tags.CheckBox(it.id).tag).performClick()
        }

        homeworks.forEach {
            rule.onNodeWithText(it.content).assertIsNotDisplayed()
            rule.onNodeWithText(it.subjectName).assertIsNotDisplayed()
        }
    }

    @Test
    fun revealSwipeDeleteActionsWorks() {
        homeworks.forEach {
            rule.onNodeWithText(it.content).assertIsDisplayed()
            rule.onNodeWithText(it.subjectName).assertIsDisplayed()
        }

        homeworks.reversed().forEach {
            rule.onAllNodesWithTag(Tags.RevealSwipe(it.id).tag).onLast()
                .assertIsDisplayed()
                .assertHasClickAction()
                .performTouchInput { swipeLeft(durationMillis = 1) }

            rule.onNodeWithTag(Tags.DeleteAction(it.id).tag, useUnmergedTree = true)
                .assertIsDisplayed()
                .performClick()
        }

        homeworks.forEach {
            rule.onNodeWithText(it.content).assertIsNotDisplayed()
            rule.onNodeWithText(it.subjectName).assertIsNotDisplayed()
        }
    }
}