package com.example.lessonsschedulemanagerv2.ui.screen

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.R
import com.yetk.yetkschedule.data.local.model.Homework
import com.yetk.yetkschedule.ui.AutocompleteTextField
import com.yetk.yetkschedule.other.filterDropdownMenu
import com.yetk.yetkschedule.ui.ButtonSection
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray70
import com.yetk.yetkschedule.ui.theme.Gray80
import com.yetk.yetkschedule.ui.theme.Gray90
import com.yetk.yetkschedule.ui.theme.Green

private const val TAG = "HomeworkDetailScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkDetailScreen(
    homeworkId: Int?,
    isChecked: Boolean,
    onCheck: () -> Unit,
    onNavigateUp: () -> Unit
) {
    //TODO get homework by id from db
    val homework = if (homeworkId != null) Homework(
        id = homeworkId,
        content = "some home work ex 1 page 6",
        "Science"
    ) else null

    val bodyMedium = MaterialTheme.typography.bodyMedium

    var subjectTfValue by remember {
        mutableStateOf(TextFieldValue(text = homework?.subjectName ?: ""))
    }
    var subjectsOptions by remember {
        mutableStateOf(listOf<String>())
    }
    var subjectMenuExpanded by remember {
        mutableStateOf(false)
    }

    var homeworkTfValue by remember {
        mutableStateOf(homework?.content ?: "")
    }

    val all =
        listOf("Math", "Biology", "Physics", "PE", "Science", "English", "Russian")

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateUp()
                        Log.d(TAG, "Up on homeworkDetail")
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
                                checkedColor = Green,
                            ),
                        )

                        IconButton(onClick = {
                            //TODO delete lesson item from db
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
                    value = subjectTfValue,
                    setValue = { value ->
                        subjectMenuExpanded = true
                        subjectTfValue = value
                        subjectsOptions =
                            all.filterDropdownMenu(value)
                    },
                    onDismissRequest = { subjectMenuExpanded = false },
                    dropDownExpanded = subjectMenuExpanded,
                    list = subjectsOptions,
                    label = "Subject"
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 116.dp),
                    value = homeworkTfValue,
                    onValueChange = { homeworkTfValue = it },
                    placeholder = {
                        Text(
                            text = "Homewrok",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray70
                        )
                    },
                    textStyle = bodyMedium,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Gray90,
                        cursorColor = Green,
                        focusedBorderColor = Green,
                        focusedLabelColor = Green,
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
                        .fillMaxWidth(),
                    positiveButtonText = if (homework != null) "Save" else "Add",
                    negativeButtonText = "Cancel",
                    onPositiveButtonClick = {
                        //TODO  Add homework to db
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
