package com.yetk.for_student.ui.screen.homework

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.component.AutoComplete
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.component.YetkFilledButton
import com.yetk.designsystem.component.YetkMultilineTextField
import com.yetk.designsystem.component.YetkOutlinedButton
import com.yetk.designsystem.component.YetkTopBar
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.for_student.common.Const.AUTO_INCREMENT_ID
import com.yetk.for_student.common.DetailScreenType
import com.yetk.for_student.domain.model.Homework

//TODO call snackbar on tob bar actions
//TODO after clicking on subjectName in AutocompleteTextField set it's cursor to an end

private const val TAG = "HomeworkDetailScreen"

@Composable
internal fun HomeworkDetailRoute(
    detailScreenType: DetailScreenType,
    homeworkSubject: String,
    homeworkContent: String,
    subjectsNames: List<String>,
    checkCorrectInput: (String, String) -> Boolean,
    onNavigateUp: () -> Unit,
    onHomeworkCheck: (Int) -> Unit,
    onHomeworkDelete: (Int) -> Unit,
    onHomeworkInsert: (homework: Homework) -> Unit,
    onHomeworkUpdate: (homework: Homework) -> Unit,
) {
    HomeworkDetailScreen(
        detailScreenType = detailScreenType,
        subjectsNames = subjectsNames,
        onNavigateUp = onNavigateUp,
        onHomeworkCheck = onHomeworkCheck,
        onHomeworkDelete = onHomeworkDelete,
        onHomeworkInsert = onHomeworkInsert,
        onHomeworkUpdate = onHomeworkUpdate,
        homeworkSubject = homeworkSubject,
        homeworkContent = homeworkContent,
        checkCorrectInput = checkCorrectInput,
    )
}

@Composable
fun HomeworkDetailScreen(
    homeworkSubject: String,
    homeworkContent: String,
    detailScreenType: DetailScreenType,
    subjectsNames: List<String>,
    checkCorrectInput: (String, String) -> Boolean,
    onNavigateUp: () -> Unit,
    onHomeworkCheck: (Int) -> Unit,
    onHomeworkDelete: (Int) -> Unit,
    onHomeworkInsert: (homework: Homework) -> Unit,
    onHomeworkUpdate: (homework: Homework) -> Unit,
) {
    val isEditScreen = remember {
        detailScreenType is DetailScreenType.EditScreen
    }

    var subjectTfValue by remember {
        mutableStateOf(homeworkSubject)
    }

    var contentTfValue by remember {
        mutableStateOf(homeworkContent)
    }

    val checkBoxValue by remember {
        mutableStateOf(false)
    }

    var autoCompleteExpanded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            YetkTopBar(
                text = "",
                navigationIcon = YetkIcon.ArrowBack,
                navigationIconContentDescription = stringResource(R.string.navigate_back),
                onNavigationClick = onNavigateUp
            ) {
                if (isEditScreen) {
                    Checkbox(
                        checked = checkBoxValue,
                        onCheckedChange = {
                            onHomeworkCheck(detailScreenType.id)
                            onNavigateUp()

                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.secondary,
                        ),
                    )

                    IconButton(onClick = {
                        onHomeworkDelete(detailScreenType.id)
                        onNavigateUp()
                    }) {
                        Icon(
                            imageVector = YetkIcon.Delete,
                            contentDescription = stringResource(R.string.delete_homework_action),
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
                    items = subjectsNames,
                    value = subjectTfValue,
                    label = stringResource(R.string.subject),
                    arrowIconContentDescription = if (autoCompleteExpanded) stringResource(id = R.string.shrink_action)
                    else stringResource(R.string.expand_action),
                    expanded = autoCompleteExpanded,
                    onExpandedChange = {autoCompleteExpanded = it}
                ) {
                    subjectTfValue = it
                }

                Spacer(modifier = Modifier.height(32.dp))

                YetkMultilineTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 116.dp),
                    value = contentTfValue,
                    placeholderText = stringResource(id = R.string.homework),
                    onValueChange = {
                        contentTfValue = it
                    }
                )
            }

            ButtonSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                homeworkId = detailScreenType.id,
                isEditScreen = isEditScreen,
                subjectTfValue = subjectTfValue,
                contentTfValue = contentTfValue,
                checkCorrectInput = checkCorrectInput,
                onHomeworkInsert = onHomeworkInsert,
                onHomeworkUpdate = onHomeworkUpdate,
                onNavigateUp = onNavigateUp,
                )
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier,
    homeworkId: Int,
    isEditScreen: Boolean,
    subjectTfValue: String,
    contentTfValue: String,
    checkCorrectInput: (String, String) -> Boolean,
    onHomeworkInsert: (homework: Homework) -> Unit,
    onHomeworkUpdate: (homework: Homework) -> Unit,
    onNavigateUp: () -> Unit
) {
    val positiveBtnTextId = if (isEditScreen) R.string.save_action else R.string.add_action

    Column(
        modifier = modifier
    ) {
        YetkDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (checkCorrectInput(subjectTfValue, contentTfValue)) {
                YetkFilledButton(
                    text = stringResource(positiveBtnTextId)
                ) {
                    if (isEditScreen) onHomeworkUpdate(Homework(homeworkId, contentTfValue, subjectTfValue))
                    else onHomeworkInsert(Homework(AUTO_INCREMENT_ID, contentTfValue, subjectTfValue))
                    onNavigateUp()
                }
            }
            YetkOutlinedButton(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = stringResource(R.string.cancel_action),
            ) {
                onNavigateUp()
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
                detailScreenType = DetailScreenType.AddScreen,
                subjectsNames = emptyList<String>(),
                onNavigateUp = {},
                onHomeworkCheck = {},
                onHomeworkInsert = {},
                onHomeworkDelete = {},
                homeworkSubject = "fdaf",
                homeworkContent = "fadf",
                checkCorrectInput = {_, _ -> false},
                onHomeworkUpdate = {},
            )
        }
    }
}
