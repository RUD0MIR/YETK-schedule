package com.yetk.yetkschedule.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yetk.yetkschedule.R
import com.yetk.yetkschedule.data.local.model.Lesson
import com.yetk.yetkschedule.other.NoRippleInteractionSource
import com.yetk.yetkschedule.other.WeekState
import com.example.lessonsschedulemanagerv2.ui.dragdrop.DragDropList
import com.example.lessonsschedulemanagerv2.ui.dragdrop.move
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray70
import com.yetk.yetkschedule.ui.theme.Gray90
import com.yetk.yetkschedule.ui.theme.Green
import com.yetk.yetkschedule.ui.theme.Red
import com.yetk.yetkschedule.ui.theme.White
import com.yetk.yetkschedule.ui.theme.WhiteDisabled
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yetk.yetkschedule.data.local.FakeData
import com.yetk.yetkschedule.other.LessonWeekState
import com.yetk.yetkschedule.other.name
import com.yetk.yetkschedule.ui.theme.Blue
import com.yetk.yetkschedule.ui.theme.Inter
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    bottomBarPadding: PaddingValues,
) {
    val fakeSchedule = FakeData.lessonListItems
    var previewWeekState by remember {
        mutableStateOf(WeekState.LOWER_WEEK)
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val schedulePeriod by remember {
        mutableStateOf("06.11.23 - 11.11.23")
    }
    val studentsGroup by remember {
        mutableStateOf("20 КИС-1")
    }

    val actualWeekState by remember {
        mutableStateOf(WeekState.LOWER_WEEK)
    }

    Scaffold(
        modifier = Modifier.padding(bottomBarPadding),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(end = 16.dp),
                title = {
                    Text(
                        text = "Schedule",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                actions = {
                    IconButton(onClick = {
                        previewWeekState = !previewWeekState
                    }
                    ) {
                        if (previewWeekState == WeekState.UPPER_WEEK) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = R.drawable.to_lower_week),
                                contentDescription = "To lower week",
                                tint = Gray50
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = R.drawable.ic_to_upper_week),
                                contentDescription = "To upper week",
                                tint = Gray50
                            )
                        }

                    }
                }
            )
        },
    ) { topBarPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = schedulePeriod,
                    fontFamily = Inter,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray70
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = studentsGroup,
                    fontFamily = Inter,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray70
                )
            }

            Text(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                text = actualWeekState.name(),
                fontFamily = Inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if(previewWeekState == actualWeekState) Blue else Gray70
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp), thickness = 1.dp, color = Gray90
            )

            HorizontalWeekPager() { currentPage ->
                val currentLessons = fakeSchedule[currentPage]

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(count = currentLessons.size) { i ->
                        Column() {
                            LessonListItem(
                                currentLessons[i],
                            )
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp), thickness = 1.dp, color = Gray90
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalWeekPager(pageContent: @Composable (currentPage: Int) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val weekTabsTitles = context.resources.getStringArray(R.array.week_days)
    val pagerState = rememberPagerState(pageCount = weekTabsTitles.size)

    TabRow(
        modifier = Modifier.height(40.dp),
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.background,
        indicator = {},
        divider = {}
    ) {
        weekTabsTitles.forEachIndexed { i: Int, tab: String ->
            val isSelected = pagerState.currentPage == i
            Tab(
                selected = isSelected,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(i)
                    }
                },
                interactionSource = NoRippleInteractionSource()
            ) {
                Text(
                    text = tab,
                    color = if (isSelected) Blue else Gray70,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp), thickness = 1.dp, color = Gray90
    )

    HorizontalPager(state = pagerState) { page ->
        pageContent(page)
    }
}

@Composable
fun LessonListItem(
    lesson: Lesson,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (lesson.isDisabled) WhiteDisabled else White
        ),
    ) {
        Row(
            modifier = Modifier
                .clickable {

                }
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.height(16.dp),
                    text = lesson.startTime,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
                )
                Text(
                    modifier = Modifier.height(13.dp),
                    text = lesson.endTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (lesson.isDisabled) Gray70.copy(alpha = 0.38f) else Gray70
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = lesson.subject,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
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
                                painter = painterResource(id = R.drawable.ic_location),
                                contentDescription = "Room",
                                tint = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = lesson.room,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (lesson.isDisabled) Gray70.copy(alpha = 0.38f) else Gray70
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(R.drawable.ic_person),
                                contentDescription = "Teacher",
                                tint = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = lesson.teacher,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (lesson.isDisabled) Gray70.copy(alpha = 0.38f) else Gray70
                            )
                        }
                    }
                    Row {
                        when (lesson.weekState) {
                            LessonWeekState.EVERY_WEEK -> {}
                            LessonWeekState.UPPER_WEEK -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clock_upper_week),
                                    contentDescription = "Upper week",
                                    tint = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
                                )
                            }

                            LessonWeekState.LOWER_WEEK -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clock_lower_week),
                                    contentDescription = "Lower week",
                                    tint = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}