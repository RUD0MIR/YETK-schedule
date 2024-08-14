package com.yetk.for_student.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yetk.designsystem.component.YetkAddButton
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.for_student.common.Const.CHECKBOX_ANIM_DURATION
import com.yetk.for_student.data.local.HomeworkViewModel
import com.yetk.model.Homework
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val TAG = "HomeworkScreen"

@Composable
internal fun HomeworkRoute(
    homeworks: List<Homework>,
    onNavigateToEditScreen: (homeworkId: Int, homeworkContent: String, homeworkSubject: String) -> Unit,
    onNavigateToAddScreen: () -> Unit,
    onHomeworkCheck: (homeworkId: Int) -> Unit,
    onHomeworkDelete: (homeworkId: Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val lifecycle = LocalLifecycleOwner.current



    HomeworkScreen(
        homeworks = homeworks,
        onNavigateToEditScreen = onNavigateToEditScreen,
        onHomeworkDelete = onHomeworkDelete,
        onHomeworkCheck = onHomeworkCheck,
        onNavigateToAddScreen = onNavigateToAddScreen,
        onShowSnackbar = onShowSnackbar
    )
}

@Composable
fun HomeworkScreen(
    homeworks: List<Homework>,
    onNavigateToEditScreen: (homeworkId: Int, homeworkContent: String, homeworkSubject: String) -> Unit,
    onHomeworkCheck: (homeworkId: Int) -> Unit,
    onHomeworkDelete: (homeworkId: Int) -> Unit,
    onNavigateToAddScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    Scaffold(
        floatingActionButton = {
            YetkAddButton() {
                onNavigateToAddScreen()
            }
        },
    ) { topBarPadding ->
        val scope = rememberCoroutineScope()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(homeworks.size) {
                val homework = homeworks[it]
                var isVisible by rememberSaveable {
                    mutableStateOf(true)
                }
                AnimatedVisibility(
                    isVisible,
                    enter = slideInHorizontally { offset -> -offset },
                    exit = slideOutHorizontally {offset -> -offset }
                ) {
                    Column {
                        val context = LocalContext.current
                        HomeworkListItem(
                            homework,
                            onCheck = { id ->
                                scope.launch {
                                    delay(CHECKBOX_ANIM_DURATION)
                                    isVisible = false

                                    if (!onShowSnackbar(
                                            context.getString(R.string.homework_completed),
                                            context.getString(R.string.cancel_action)
                                        )
                                    ) {
                                        onHomeworkCheck(id)
                                    } else {
                                        isVisible = true
                                    }
                                }
                            },
                            onItemClick = {
                                onNavigateToEditScreen(
                                    homework.id,
                                    homework.content ?: context.getString(R.string.null_content),
                                    homework.subjectName ?: context.getString(R.string.null_content)
                                )
                            },
                            onBackgroundEndClick = { id ->
                                isVisible = false
                                scope.launch {
                                    if (
                                        !onShowSnackbar(
                                            context.getString(R.string.homework_deleted),
                                            context.getString(R.string.cancel_action)
                                        )
                                    ) {
                                        onHomeworkDelete(id)
                                    } else {
                                        isVisible = true
                                    }
                                }
                            },
                        )
                        YetkDivider()
                    }
                }
            }
            topBarPadding
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeworkListItem(
    homework: Homework,
    onBackgroundEndClick: (id: Int) -> Unit,
    onItemClick: () -> Unit,
    onCheck: (id: Int) -> Unit
) {
    var isChecked by remember {
        mutableStateOf(false)
    }

    RevealSwipe(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundCardEndColor = MaterialTheme.colorScheme.primaryContainer,
        backgroundCardContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        directions = setOf(
            RevealDirection.EndToStart
        ),
        hiddenContentEnd = {
             Icon(
                modifier = Modifier.padding(horizontal = 25.dp),
                imageVector = YetkIcon.Delete,
                contentDescription = null,
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
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
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
                            onCheck(homework.id)
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeworkScreenPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface(tonalElevation = 5.dp) {
            HomeworkScreen(
                emptyList<Homework>(),
                { _, _, _ -> },
                {},
                {},
                {},
                { _, _ -> false }
            )
        }
    }
}

