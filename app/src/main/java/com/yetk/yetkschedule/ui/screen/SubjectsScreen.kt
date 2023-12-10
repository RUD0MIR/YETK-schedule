package com.yetk.yetkschedule.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.data.local.model.Subject
import com.yetk.yetkschedule.data.local.model.Teacher
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray60
import com.yetk.yetkschedule.ui.theme.Gray90

private val subjectsList = mutableListOf(
    Subject(id = 0, "Subject 1", listOf(Teacher(0, "Колобова Р.В."), Teacher(0, "Колобова Р.В."))),
    Subject(id = 1, "Subject 2", listOf(Teacher(1, "Меркушев Мечеслав Николаевич"))),
    Subject(id = 2, "Subject 3", listOf(Teacher(2, "Колобова Р.В."))),
    Subject(id = 3, "Subject 4", listOf(Teacher(3, "Меркушев Мечеслав Николаевич"))),
    Subject(id = 4, "Subject 5", listOf(Teacher(4, "Колобова Р.В."))),
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsScreen(
    bottomBarPadding: PaddingValues
) {
    Scaffold(
        modifier = Modifier.padding(bottomBarPadding),
        topBar = {
            Column() {
                TopAppBar(
                    modifier = Modifier.padding(end = 16.dp),
                    title = {
                        Text(
                            text = "Предметы",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp), thickness = 1.dp, color = Gray90
                )
            }
        },
    ) { topBarPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding)
                .padding(start = 16.dp)
        ) {
            items(subjectsList.size) {
                SubjectListItem(subjectsList[it])
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

    val modifier = if (expanded) Modifier else  Modifier.height(52.dp)

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                                text = it.fullName,
                                style = MaterialTheme.typography.bodySmall,
                                color = Gray60
                            )
                        }
                    }

            }

            IconButton(
                modifier = Modifier.padding(top = 4.dp),
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Закрыть" else "Раскрыть",
                    tint = Gray50
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 1.dp,
            color = Gray90
        )
    }
}