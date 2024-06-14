package com.yetk.for_student.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.designsystem.component.AutoComplete
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.component.YetkFilledButton
import com.yetk.designsystem.component.YetkMultilineTextField
import com.yetk.designsystem.component.YetkOutlinedButton
import com.yetk.designsystem.component.YetkTopBar
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.data.local.viewmodel.HomeworkEvent
import com.yetk.for_student.data.local.viewmodel.HomeworkState
import com.yetk.for_student.data.local.viewmodel.HomeworkViewModel
import com.yetk.model.Homework

//TODO top bar buttons doesn't work

private const val TAG = "HomeworkDetailScreen"

@Composable
internal fun HomeworkDetailRoute(
    homeworkId: Int = -1,
    homeworkContent: String,
    homeworkSubject: String,
    onNavigateUp: () -> Unit,
    viewModel: HomeworkViewModel = hiltViewModel()
) {
    HomeworkDetailScreen(
        homeworkId = homeworkId,
        homeworkContent = homeworkContent,
        homeworkSubject = homeworkSubject,
        onNavigateUp = onNavigateUp,
        onHomeworkCheck = { viewModel.checkHomework(homeworkId) },
        onHomeworkDelete = { viewModel.deleteHomework(homeworkId) },
        onHomeworkInsert = { viewModel.insertHomework(it) },
        onHomeworkUpdate = { viewModel.updateHomework(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkDetailScreen(
    homeworkContent: String,
    homeworkSubject: String,
    homeworkId: Int,
    onNavigateUp: () -> Unit,
    onHomeworkCheck: () -> Unit,
    onHomeworkDelete: () -> Unit,
    onHomeworkInsert: (homework: Homework) -> Unit,
    onHomeworkUpdate: (homework: Homework) -> Unit,
) {
    var subjectTfValue by remember {
        mutableStateOf(homeworkSubject)
    }

    var contentTfValue by remember {
        mutableStateOf(homeworkContent)
    }

    val isScreenForEditItem by remember {
        mutableStateOf(homeworkId != -1)
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
                if (isScreenForEditItem) {
                    Checkbox(
                        checked = checkBoxValue,
                        onCheckedChange = {
                            onHomeworkCheck()
                            onNavigateUp()

                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.secondary,
                        ),
                    )

                    IconButton(onClick = {
                        onHomeworkDelete()
                        onNavigateUp()
                    }) {
                        Icon(
                            imageVector = YetkIcon.Delete,
                            contentDescription = "Delete lesson",
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
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                AutoComplete(
                    testDropDownItems,
                    subjectTfValue,
                    "Предмет"
                ) {
                    subjectTfValue = it
                }

                Spacer(modifier = Modifier.height(32.dp))

                YetkMultilineTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 116.dp),
                    value = contentTfValue,
                    placeholderText = "Домашнее задание",
                    onValueChange = {
                        contentTfValue = it
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
                    if (subjectTfValue.isNotBlank() || contentTfValue.isNotBlank()) {
                        YetkFilledButton(
                            text = if (isScreenForEditItem) "Сохранить" else "Добавить"
                        ) {
                            if (isScreenForEditItem) {
                                onHomeworkUpdate(Homework(homeworkId, contentTfValue, subjectTfValue))
                            } else {
                                onHomeworkInsert(Homework(0, contentTfValue, subjectTfValue))
                            }
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeworkDetailPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface(tonalElevation = 5.dp) {
            HomeworkDetailScreen(
                homeworkId = -1,
                homeworkSubject = "subject",
                homeworkContent = "content",
                onNavigateUp = {},
                onHomeworkCheck = {},
                onHomeworkInsert = {},
               onHomeworkDelete = {}
            ) {}
        }
    }
}
