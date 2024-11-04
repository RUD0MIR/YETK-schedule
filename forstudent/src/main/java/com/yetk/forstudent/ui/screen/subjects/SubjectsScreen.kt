package com.yetk.forstudent.ui.screen.subjects

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.component.YetkExpandToggle
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.forstudent.common.Response
import com.yetk.forstudent.common.Tags
import com.yetk.forstudent.domain.model.CollegeGroup
import com.yetk.forstudent.domain.model.Subject
import com.yetk.forstudent.ui.screen.StudentViewModel
import com.yetk.ui.ErrorScreen
import com.yetk.ui.LoadingScreen

private const val TAG = "SubjectsScreen"

@Composable
internal fun SubjectsRoute(viewModel: StudentViewModel = hiltViewModel()) {
    SubjectsScreen(
        viewModel.collegeGroup.collectAsStateWithLifecycle().value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsScreen(collegeGroupData: Response<CollegeGroup>) {
    when (collegeGroupData) {
        is Response.Loading -> LoadingScreen()
        is Response.Failure ->
            ErrorScreen(
                message = stringResource(id = R.string.error_screen_message)
            )
        is Response.Success -> {
            LazyColumn(
                modifier =
                Modifier
                    .fillMaxSize()
            ) {
                items(collegeGroupData.data.subjects.size) {
                    SubjectListItem(collegeGroupData.data.subjects[it], it)
                }
            }
        }
    }
}

@Composable
fun SubjectListItem(subject: Subject, index: Int = -1) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
    ) {
        Card(
            colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = subject.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    AnimatedVisibility(visible = expanded) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            subject.teachers.forEach {
                                Text(
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
                YetkExpandToggle(
                    Modifier
                        .padding(top = 4.dp)
                        .testTag(Tags.ExpandToggle(index).tag),
                    expanded = expanded,
                    contentDescription =
                    if (expanded) {
                        stringResource(id = R.string.shrink_action)
                    } else {
                        stringResource(id = R.string.expand_action)
                    }
                ) {
                    expanded = !expanded
                }
            }
            YetkDivider()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleScreenPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface(tonalElevation = 5.dp) {
            SubjectsScreen(
                collegeGroupData =
                Response.Success(
                    CollegeGroup(
                        subjects =
                        listOf(
                            Subject("Subject", listOf("teacher")),
                            Subject("Subject", listOf("teacher", "teacher")),
                            Subject("Subject", listOf("teacher")),
                            Subject("Subject", listOf("teacher")),
                            Subject("Subject", listOf("teacher", "teacher", "teacher"))
                        ),
                        id = "",
                        login = "",
                        name = "",
                        password = "",
                        relevantFromTo = "",
                        lessons = listOf()
                    )
                )
            )
        }
    }
}
