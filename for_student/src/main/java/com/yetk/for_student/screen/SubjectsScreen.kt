package com.yetk.for_student.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.component.YetkExpandToggle
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.Gray60
import com.yetk.designsystem.theme.White
import com.yetk.for_student.data.remote.viewmodel.StudentViewModel
import com.yetk.model.CollegeGroup
import com.yetk.model.Response
import com.yetk.model.Subject
import com.yetk.ui.ErrorScreen
import com.yetk.ui.LoadingScreen

private const val TAG = "SubjectsScreen"

@Composable
internal fun SubjectsRoute(
    viewModel: StudentViewModel = hiltViewModel(),
) {
    SubjectsScreen(
        viewModel.collegeGroup.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsScreen(
    collegeGroupData: Response<CollegeGroup>
) {
    when (collegeGroupData) {
        is Response.Loading -> LoadingScreen()
        is Response.Failure -> ErrorScreen()
        is Response.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            ) {
                items(collegeGroupData.data.subjects.size) {
                    SubjectListItem(collegeGroupData.data.subjects[it])
                }
            }
        }
    }
}

@Composable
fun SubjectListItem(
    subject: Subject
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val modifier = if (expanded) Modifier else Modifier.height(52.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = subject.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray50
                )
                Column(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                    subject.teachers.forEach {
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = Gray60
                        )
                    }
                }

            }

            YetkExpandToggle(Modifier.padding(top = 4.dp), expanded = expanded) {
                expanded = !expanded
            }
        }
        YetkDivider()
    }
}