package com.yetk.for_student.ui.screen.schedule

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yetk.designsystem.component.LowerUpperWeekToggle
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.Inter
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.R
import com.yetk.for_student.WeekState
import com.yetk.for_student.common.Response
import com.yetk.for_student.domain.model.BellSchedule
import com.yetk.for_student.domain.model.CollegeGroup
import com.yetk.for_student.domain.model.Lesson
import com.yetk.for_student.matchesWeekState
import com.yetk.for_student.ui.screen.StudentViewModel
import com.yetk.ui.ErrorScreen
import com.yetk.ui.LoadingScreen
import com.yetk.ui.NoRippleInteractionSource
import kotlinx.coroutines.launch

private const val TAG = "ScheduleScreen"

@Composable
internal fun ScheduleRoute(
    collegeGroup: Response<CollegeGroup>,
    isLowerWeek: Response<Boolean>,
    bellSchedule: Response<BellSchedule>,
) {
    ScheduleScreen(
        collegeGroup = collegeGroup,
        isLowerWeek = isLowerWeek,
        bellSchedule = bellSchedule
    )
}

@Composable
fun ScheduleScreen(
    collegeGroup: Response<CollegeGroup>,
    isLowerWeek: Response<Boolean>,
    bellSchedule: Response<BellSchedule>,

    ) {

    if (collegeGroup is Response.Loading || isLowerWeek is Response.Loading || bellSchedule is Response.Loading) {
        LoadingScreen()
    }

    if (collegeGroup is Response.Failure || isLowerWeek is Response.Failure || bellSchedule is Response.Failure) {
        ErrorScreen(message = stringResource(R.string.error_screen_message))
    }

    if (collegeGroup is Response.Success && isLowerWeek is Response.Success && bellSchedule is Response.Success) {

        var isLowerWeekPreview by remember {
            mutableStateOf(isLowerWeek.data)
        }

        Column(Modifier.fillMaxSize()) {
            ScheduleDataSection(
                isLowerWeekPreview = isLowerWeekPreview,
                isLowerWeekValue = isLowerWeek.data,
                collegeGroupData = collegeGroup.data
            ) {
                isLowerWeekPreview = !isLowerWeekPreview
            }

            HorizontalWeekPager { currentPage ->
                var currentLessons = remember {
                    collegeGroup.data.lessons.filter { it.dayOfWeek == currentPage }
                }

                if (currentLessons.isEmpty()) {
                    EmptyListPlaceholder(modifier = Modifier.fillMaxSize())
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(count = currentLessons.size) { i ->
                            AnimatedVisibility(
                                visible = currentLessons[i].weekState.matchesWeekState(
                                    isLowerWeekPreview
                                ),
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Column {
                                    LessonListItem(
                                        currentLessons[i],
                                        bellSchedule.data
                                    )
                                    YetkDivider()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun EmptyListPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.width(250.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.no_lessons_today_message),
            fontFamily = Inter,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalWeekPager(
    modifier: Modifier = Modifier,
    pageContent: @Composable (currentPage: Int) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val weekTabsTitles = context.resources.getStringArray(R.array.week_days)
    val pagerState = rememberPagerState(pageCount = weekTabsTitles.size)

    Column(modifier) {
        TabRow(
            modifier = Modifier
                .height(40.dp),
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            divider = {}
        ) {
            weekTabsTitles.forEachIndexed { i: Int, tab: String ->
                val isSelected = pagerState.currentPage == i
                Tab(
                    modifier = Modifier.padding(12.dp),
                    selected = isSelected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(i)
                        }
                    },
                    interactionSource = NoRippleInteractionSource(),
                    selectedContentColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = tab,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        YetkDivider()

        HorizontalPager(modifier = Modifier, state = pagerState) { page ->
            pageContent(page)
        }
    }
}

@Composable
fun LessonListItem(
    lesson: Lesson,
    bellSchedule: BellSchedule
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
    ) {
        Row(
            modifier = Modifier
                .clickable {}
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = lesson.startTime(bellSchedule),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = lesson.endTime(bellSchedule),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = lesson.subject,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = YetkIcon.LocationOn,
                                contentDescription = stringResource(R.string.room_icon)
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = lesson.rooms[0],//TODO make ui for multiple rooms
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                imageVector = YetkIcon.Person,
                                contentDescription = stringResource(R.string.teacher_icon),
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = lesson.teachers[0], //TODO make ui for multiple teachers
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                    when (lesson.weekState) {
                        WeekState.EveryWeek.id -> {}
                        WeekState.UpperWeek.id -> {
                            Icon(
                                imageVector = YetkIcon.ClockUpperWeek,
                                contentDescription = stringResource(WeekState.UpperWeek.textId),
                            )
                        }

                        WeekState.LowerWeek.id -> {
                            Icon(
                                imageVector = YetkIcon.ClockLowerWeek,
                                contentDescription = stringResource(WeekState.LowerWeek.textId),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleDataSection(
    isLowerWeekPreview: Boolean?,
    isLowerWeekValue: Boolean?,
    collegeGroupData: CollegeGroup,
    onWeekToggle: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.padding(start = 16.dp),
        ) {
            Text(
                text = collegeGroupData.relevantFromTo,
                fontFamily = Inter,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (isLowerWeekValue != null) {
                Text(
                    text = if (isLowerWeekValue)
                        stringResource(WeekState.LowerWeek.textId) else
                        stringResource(WeekState.UpperWeek.textId),
                    fontFamily = Inter,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =
                    if (isLowerWeekValue == isLowerWeekPreview)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = collegeGroupData.name,
            fontFamily = Inter,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (isLowerWeekPreview != null) {
            LowerUpperWeekToggle(
                modifier = Modifier.padding(end = 12.dp),
                contentDescription = if (isLowerWeekPreview) stringResource(R.string.to_upper_week_aciton) else stringResource(
                    R.string.to_lower_week_action
                ),
                isLowerWeek = isLowerWeekPreview
            ) {
                onWeekToggle()
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleScreenPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface(tonalElevation = 5.dp) {
            ScheduleScreen(
                collegeGroup = Response.Success(
                    CollegeGroup(
                        relevantFromTo = "22.01.24 - 27.01.24",
                        lessons = listOf(
                            Lesson(
                                dayOfWeek = 0,
                                isCanceled = false,
                                number = 1,
                                rooms =
                                listOf("304", "108"),
                                subject = "Английский язык",
                                teachers = listOf("Колобова Р.В.", "Фомина Р.И."),
                                weekState = 0
                            ),
                            Lesson(
                                dayOfWeek = 0,
                                isCanceled = false,
                                number = 1,
                                rooms =
                                listOf("304", "108"),
                                subject = "Математика",
                                teachers = listOf("Колобова Р.В.", "Фомина Р.И."),
                                weekState = 0
                            )
                        ),
                        id = "esse",
                        login = "pertinax",
                        name = "Leland Rosales",
                        password = "novum",
                        subjects = listOf()
                    )
                ),
                isLowerWeek = Response.Success(true),
                bellSchedule = Response.Success(BellSchedule())
            )
        }
    }
}
