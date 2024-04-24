package com.yetk.for_student.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.Gray70
import com.yetk.designsystem.theme.Gray80
import com.yetk.designsystem.theme.Gray90
import com.yetk.designsystem.theme.Inter
import com.yetk.designsystem.theme.WhiteDisabled
import com.yetk.for_student.R
import com.yetk.for_student.matches
import com.yetk.model.BellSchedule
import com.yetk.model.CollegeGroup
import com.yetk.model.Lesson
import com.yetk.model.Response
import com.yetk.model.Response.Loading.isFailure
import com.yetk.model.Response.Loading.isLoading
import com.yetk.model.Response.Loading.isSuccess
import com.yetk.ui.ErrorScreen
import com.yetk.ui.LoadingScreen
import com.yetk.ui.NoRippleInteractionSource
import kotlinx.coroutines.launch

private const val TAG = "ScheduleScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    bottomBarPadding: PaddingValues,
    viewModel: com.yetk.for_student.data.remote.viewmodel.StudentViewModel = hiltViewModel()
) {
    val collegeGroup = viewModel.collegeGroup.value
    val isLowerWeek = viewModel.isLowerWeek.value
    val bellSchedule = viewModel.bellSchedule.value

    var isLowerWeekPreview by remember {
        mutableStateOf<Boolean?>(null)
    }
    var collegeGroupData by remember {
        mutableStateOf(CollegeGroup())
    }
    var isLowerWeekValue by remember {
        mutableStateOf<Boolean?>(null)
    }
    var bellScheduleData by remember {
        mutableStateOf(BellSchedule())
    }

    when {
        isLoading(collegeGroup, isLowerWeek, bellSchedule) -> {
            LoadingScreen(topBarTitle = "Расписание")
            Log.d(TAG, "Loading")
        }
        isFailure(collegeGroup, isLowerWeek, bellSchedule) -> {
            Log.d(TAG, "Failure")
            ErrorScreen(message = "Хмм... что-то пошло не так", topBarTitle = "Расписание")
        }
        isSuccess(collegeGroup, isLowerWeek, bellSchedule) -> {
            Log.d(TAG, "Success")
            collegeGroupData = (collegeGroup as Response.Success).data
            isLowerWeekValue = (isLowerWeek as Response.Success).data
            bellScheduleData = (bellSchedule as Response.Success).data
            isLowerWeekPreview = isLowerWeekValue

            Scaffold(
                modifier = Modifier.padding(bottomBarPadding),
                topBar = {
                    Column(Modifier.fillMaxWidth()) {
                        TopAppBar(
                            modifier = Modifier.padding(end = 16.dp),
                            title = {
                                Text(
                                    text = "Расписание",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            },
                            actions = {
                                if (isLowerWeekPreview != null) {
                                    IconButton(onClick = {
                                        isLowerWeekPreview = !isLowerWeekPreview!!
                                    }
                                    ) {
                                        if (!isLowerWeekPreview!!) {
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
                            }
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp)
                            ) {
                                Text(
                                    text = collegeGroupData.relevantFromTo,
                                    fontFamily = Inter,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Gray70
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    text = collegeGroupData.name,
                                    fontFamily = Inter,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Gray70
                                )
                            }

                            if (isLowerWeekValue != null) {
                                Text(
                                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                                    text = if (isLowerWeekValue!!) "Нижняя неделя" else "Верхняя неделя",
                                    fontFamily = Inter,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isLowerWeekValue == isLowerWeekPreview) MaterialTheme.colorScheme.secondary else Gray70
                                )
                            }
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp), thickness = 1.dp, color = Gray90
                        )
                    }
                },
            ) { topBarPadding ->
                HorizontalWeekPager(
                    modifier = Modifier.padding(top = 150.dp)
                ) { currentPage ->
                    var currentLessons = emptyList<Lesson>()

                    if(isLowerWeekPreview != null) {
                        currentLessons = collegeGroupData.lessons.filter {
                            it.dayOfWeek == currentPage && it.weekState.matches(isLowerWeekPreview!!)
                        }
                    }


                    if (currentLessons.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.width(250.dp),
                                textAlign = TextAlign.Center,
                                text = "На сегодня занятий не запланированно",
                                fontFamily = Inter,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = Gray80
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(count = currentLessons.size) { i ->
                                Column() {
                                    LessonListItem(
                                        currentLessons[i],
                                        bellScheduleData
                                    )
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        thickness = 1.dp,
                                        color = Gray90
                                    )
                                }
                            }
                        }
                    }
                }
                topBarPadding
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalWeekPager(modifier: Modifier = Modifier, pageContent: @Composable (currentPage: Int) -> Unit) {
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
                        color = if (isSelected) MaterialTheme.colorScheme.secondary else Gray70,
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
        colors = CardDefaults.cardColors(
            containerColor = if (lesson.isCanceled) WhiteDisabled else White
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
                    text = lesson.startTime(bellSchedule),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (lesson.isCanceled) Gray50.copy(alpha = 0.38f) else Gray50
                )
                Text(
                    modifier = Modifier.height(13.dp),
                    text = lesson.endTime(bellSchedule),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (lesson.isCanceled) Gray70.copy(alpha = 0.38f) else Gray70
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = lesson.subject,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = if (lesson.isCanceled) Gray50.copy(alpha = 0.38f) else Gray50
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
                                tint = if (lesson.isCanceled) Gray50.copy(alpha = 0.38f) else Gray50
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = lesson.rooms[0],//TODO make ui for multiple rooms
                                style = MaterialTheme.typography.bodySmall,
                                color = if (lesson.isCanceled) Gray70.copy(alpha = 0.38f) else Gray70
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(R.drawable.ic_person),
                                contentDescription = "Teacher",
                                tint = if (lesson.isCanceled) Gray50.copy(alpha = 0.38f) else Gray50
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = lesson.teachers[0], //TODO make ui for multiple teachers
                                style = MaterialTheme.typography.bodySmall,
                                color = if (lesson.isCanceled) Gray70.copy(alpha = 0.38f) else Gray70
                            )
                        }
                    }
                    Row {
                        when (lesson.weekState.toInt()) {
                            0 -> {}
                            1 -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clock_upper_week),
                                    contentDescription = "Upper week",
                                    tint = if (lesson.isCanceled) Gray50.copy(alpha = 0.38f) else Gray50
                                )
                            }
                            -1 -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clock_lower_week),
                                    contentDescription = "Lower week",
                                    tint = if (lesson.isCanceled) Gray50.copy(alpha = 0.38f) else Gray50
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}