package com.yetk.for_student.ui.screen.bell_schedule

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.for_student.common.Response
import com.yetk.for_student.common.parseNhNmin
import com.yetk.for_student.domain.model.BellSchedule
import com.yetk.for_student.ui.screen.StudentViewModel
import com.yetk.ui.ErrorScreen
import com.yetk.ui.LoadingScreen

private const val TAG = "BellScheduleScreen"

@Composable
internal fun BellScheduleRoute(
    bellSchedule: Response<BellSchedule>
) {
    BellScheduleScreen(
        bellSchedule
    )
}

@Composable
fun BellScheduleScreen(
    bellSchedule: Response<BellSchedule>
) {
    val context = LocalContext.current
    when (bellSchedule) {
        is Response.Loading -> LoadingScreen()
        is Response.Failure -> ErrorScreen(message = stringResource(id = R.string.error_screen_message))
        is Response.Success -> {
            val lessonDurationMin = bellSchedule.data.lessonDurationMin.parseNhNmin(context)
            val lessonsTime = bellSchedule.data.lessonsTime

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    item {
                        BellScheduleHeader(stringResource(R.string.lessons_duration), lessonDurationMin)
                    }

                    items(lessonsTime.size) {
                        BellScheduleListItem(
                            number = it + 1,
                            time = lessonsTime[it]
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun BellScheduleHeader(text: String, lessonDurationMin: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = lessonDurationMin,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
fun BellScheduleListItem(
    number: Int,
    time: String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = number.toString(),
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        YetkDivider()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BellScheduleScreenPreview() {
    YetkScheduleTheme {
        Surface(tonalElevation = 5.dp) {
            BellScheduleScreen(
                bellSchedule =  Response.Success<BellSchedule>(
                    BellSchedule(
                        90,
                        lessonsTime = listOf(
                            "08:00 - 09:30",
                            "09:40 - 11:10",
                            "11:20 - 12:50",
                            "13:20 - 14:50",
                            "15:00 - 16:30",
                            "16:40 - 18:10"
                        )
                    )
                )
            )
        }
    }
}
