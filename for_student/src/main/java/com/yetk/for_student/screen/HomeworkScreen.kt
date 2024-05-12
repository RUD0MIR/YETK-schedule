package com.yetk.for_student.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.designsystem.component.YetkAddButton
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.Red
import com.yetk.designsystem.theme.White
import com.yetk.for_student.data.local.viewmodel.HomeworkEvent
import com.yetk.for_student.data.local.viewmodel.HomeworkState
import com.yetk.for_student.data.local.viewmodel.HomeworkViewModel
import com.yetk.model.Homework
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

private const val TAG = "HomeworkScreen"

@Composable
internal fun HomeworkRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNavigateToEditScreen:(id: Int) -> Unit,
    onNavigateToAddScreen: (id: Int) -> Unit,
    viewModel: HomeworkViewModel = hiltViewModel(),
) {
    HomeworkScreen(
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
        onNavigateToEditScreen = { onNavigateToEditScreen(it) },
        onNavigateToAddScreen = { onNavigateToAddScreen(it) },
        onShowSnackbar = onShowSnackbar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkScreen(
    state: HomeworkState,
    onEvent: (HomeworkEvent) -> Unit,
    onNavigateToEditScreen:(id: Int) -> Unit,
    onNavigateToAddScreen: (id: Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    Scaffold(
        floatingActionButton = {
            YetkAddButton() { onNavigateToAddScreen(-1) }
        },
    ) { topBarPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding),
        ) {
            items(state.homeworks.size) {
                Log.d(TAG, "homeworks: ${state.homeworks}")
                val homework = state.homeworks[it]
                if(homework.isVisible) {
                    Column() {
                        HomeworkListItem(
                            homework,
                            onCheck = {
                                onEvent(HomeworkEvent.HomeworkChecked(homework))
                            },
                            onItemClick = {
                                onNavigateToEditScreen(homework.id)
                                onEvent(HomeworkEvent.UpdateSubjectName( TextFieldValue(homework.subjectName ?: "")))
                                onEvent(HomeworkEvent.UpdateContent(homework.content ?: ""))
                            },
                            onBackgroundEndClick = { id ->
                                onEvent(HomeworkEvent.DeleteHomework(homework))
                            },
                        )
                        YetkDivider()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeworkListItem(
    homework: Homework,
    onBackgroundEndClick: (id: Int) -> Unit,
    onItemClick: () -> Unit,
    onCheck: () -> Unit
) {
    var isChecked by remember {
        mutableStateOf(false)
    }

    RevealSwipe(
        modifier = Modifier
            .fillMaxWidth(),
        directions = setOf(
            RevealDirection.EndToStart
        ),

        backgroundCardEndColor = Red,
        hiddenContentEnd = {
            androidx.compose.material.Icon(
                modifier = Modifier.padding(horizontal = 25.dp),
                imageVector = YetkIcon.Delete,
                contentDescription = null,
                tint = White
            )
        },
        onBackgroundEndClick = {
            onBackgroundEndClick(homework.id)
        }
    ) { shape ->
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = White),
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        onItemClick()
                    }
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text(
                        text = homework.subjectName.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Text(
                        text = homework.content.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5
                    )
                }

                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = !isChecked
                        if (isChecked) {
                            onCheck()
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.secondary,
                    ),
                )
            }
        }
    }
}


