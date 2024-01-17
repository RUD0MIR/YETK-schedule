package com.yetk.yetkschedule.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.HomeworkEvent
import com.yetk.yetkschedule.HomeworkState
import com.yetk.yetkschedule.R
import com.yetk.yetkschedule.data.local.model.Homework
import com.yetk.yetkschedule.other.filterDropdownMenu
import com.yetk.yetkschedule.ui.AutocompleteTextField
import com.yetk.yetkschedule.ui.ButtonSection
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray70
import com.yetk.yetkschedule.ui.theme.Gray80
import com.yetk.yetkschedule.ui.theme.Gray90

private const val TAG = "HomeworkDetailScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkDetailScreen(
    homeworkIndex: Int,
    state: HomeworkState,
    onEvent: (HomeworkEvent) -> Unit,
    isChecked: Boolean,
    onCheck: () -> Unit,
    onNavigateUp: () -> Unit
) {
    var homework: Homework? = null

    LaunchedEffect(key1 = state.homeworkId) {
        if (homeworkIndex != -1) {
            homework = state.homeworks[homeworkIndex]
            onEvent(HomeworkEvent.UpdateId(homework?.id ?: -1))
        } else {
            onEvent(HomeworkEvent.ClearState)
        }
    }

    val positiveBtnText = remember {
        if (homeworkIndex != -1) "Сохранить" else "Добавить"
    }

    val bodyMedium = MaterialTheme.typography.bodyMedium

    var subjectsOptions by remember {
        mutableStateOf(listOf<String>())
    }
    var subjectMenuExpanded by remember {
        mutableStateOf(false)
    }

    val all =
        listOf("Math", "Biology", "Physics", "PE", "Science", "English", "Russian")

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Go back",
                            tint = Gray50
                        )
                    }
                },
                actions = {
                    if (homework != null) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                onCheck()
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.secondary,
                            ),
                        )

                        IconButton(onClick = {
                            onEvent(HomeworkEvent.DeleteHomework(homework!!))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "Delete lesson",
                                tint = Gray50
                            )
                        }
                    }
                },
                title = {},
            )
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
                AutocompleteTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    iconId = R.drawable.ic_subject,
                    value = TextFieldValue(text = state.subjectName),
                    setValue = {
                        subjectMenuExpanded = true
                        onEvent(HomeworkEvent.UpdateSubjectName(subjectName = it.text))
                        subjectsOptions =
                            all.filterDropdownMenu(it)
                    },
                    onDismissRequest = { subjectMenuExpanded = false },
                    dropDownExpanded = subjectMenuExpanded,
                    isError = state.isSubjectNameTextFieldError,
                    list = subjectsOptions,
                    label = "Предмет",
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 116.dp),
                    value = state.content,
                    onValueChange = { onEvent(HomeworkEvent.UpdateContent(content = it)) },
                    placeholder = {
                        Text(
                            text = "Домашнее задание",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray70
                        )
                    },
                    isError = state.isContentTextFieldError,
                    textStyle = bodyMedium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Gray90,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = Gray80
                    ),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                    thickness = 1.dp,
                    color = Gray90
                )

                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    positiveButtonText = positiveBtnText,
                    negativeButtonText = "Отмена",
                    onPositiveButtonClick = {
                        onEvent(HomeworkEvent.SaveHomework)
                        onNavigateUp()
                    },
                    onNegativeButtonClick = {
                        onNavigateUp()
                    }
                )
            }
        }
    }
}
