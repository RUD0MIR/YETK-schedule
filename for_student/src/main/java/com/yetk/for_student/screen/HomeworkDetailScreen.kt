package com.yetk.for_student.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.component.AutoComplete
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.component.YetkFilledButton
import com.yetk.designsystem.component.YetkMultilineTextField
import com.yetk.designsystem.component.YetkOutlinedButton
import com.yetk.designsystem.component.YetkTopBar
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.Gray50
import com.yetk.for_student.data.local.viewmodel.HomeworkEvent
import com.yetk.for_student.data.local.viewmodel.HomeworkState
import com.yetk.for_student.data.local.viewmodel.HomeworkViewModel
import com.yetk.model.Homework

//TODO top bar buttons doesnt work

private const val TAG = "HomeworkDetailScreen"

@Composable
internal fun HomeworkDetailRoute(
    homeworkId: Int,
    onNavigateUp: () -> Unit,
    viewModel: HomeworkViewModel
) {
    HomeworkDetailScreen(
        homeworkId = homeworkId,
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
        onNavigateUp = onNavigateUp,
        onHomeworkCheck = { HomeworkEvent.HomeworkChecked(it) },
        onHomeworkDelete = { HomeworkEvent.DeleteHomework(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkDetailScreen(
    homeworkId: Int,
    state: HomeworkState,
    onEvent: (HomeworkEvent) -> Unit,
    onNavigateUp: () -> Unit,
    onHomeworkCheck: (homework: Homework) -> Unit,
    onHomeworkDelete: (homework: Homework) -> Unit
) {
    var homework: Homework? = null
    LaunchedEffect(key1 = state.homeworks) {
        if (homeworkId != -1) {
            homework = state.homeworks.find { it.id == homeworkId }
            onEvent(HomeworkEvent.UpdateId(homework?.id ?: -1))
        } else {
            onEvent(HomeworkEvent.ClearState)
        }
    }

    var subjectTfValue by remember {
        mutableStateOf(state.subjectName)
    }

    val positiveBtnText = remember {
        if (homeworkId != -1) "Сохранить" else "Добавить"
    }

    val checkBoxValue by remember {
        mutableStateOf(false)
    }

    val testDropDownItems = remember {
        mutableListOf("Математика", "Физкультура", "Информационные технологии", "Английский")
    }

    Scaffold(
        topBar = {
            YetkTopBar(
                text = "",
                navigationIcon = YetkIcon.ArrowBack,
                navigationIconContentDescription = "Go back",
                onNavigationClick = onNavigateUp
            ) {
                if (homeworkId != -1) {
                    Checkbox(
                        checked = checkBoxValue,
                        onCheckedChange = {
                            onHomeworkCheck(state.homeworks[homeworkId])
                            onNavigateUp()

                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.secondary,
                        ),
                    )

                    IconButton(onClick = {
                        onHomeworkDelete(state.homeworks[homeworkId])
                        onNavigateUp()
                    }) {
                        Icon(
                            imageVector = YetkIcon.Delete,
                            contentDescription = "Delete lesson",
                            tint = Gray50
                        )
                    }
                }
            }
        }
    ) { topBarPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize()
            ) {
                AutoComplete(
                    testDropDownItems,
                    subjectTfValue,
                    "Предмет"
                ) {
                    subjectTfValue = it
                    onEvent(HomeworkEvent.UpdateSubjectName(it))
                }

                YetkMultilineTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 116.dp),
                    value = state.content,
                    placeholderText = "Домашнее задание",
                    onValueChange = {
                        onEvent(HomeworkEvent.UpdateContent(content = it))
                    }
                )
            }

            // Button section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                YetkDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (state.subjectName.isNotBlank() || state.content.isNotBlank()) {
                        YetkFilledButton(
                            text = positiveBtnText
                        ) {
                            onEvent(HomeworkEvent.SaveHomework)
                            onNavigateUp()
                        }
                    }
                    YetkOutlinedButton(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Отмена",
                    ) {
                        onNavigateUp()
                    }
                }
            }
        }
    }
}
