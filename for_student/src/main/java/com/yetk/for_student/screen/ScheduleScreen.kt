package com.yetk.for_student.screen

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yetk.designsystem.component.LowerUpperWeekToggle
import com.yetk.designsystem.component.YetkDivider
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.Gray70
import com.yetk.designsystem.theme.Gray80
import com.yetk.designsystem.theme.Inter
import com.yetk.designsystem.theme.White
import com.yetk.designsystem.theme.WhiteDisabled
import com.yetk.for_student.R
import com.yetk.for_student.data.remote.viewmodel.StudentViewModel
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

@Composable
internal fun ScheduleRoute(
    viewModel: StudentViewModel = hiltViewModel(),
) {
    ScheduleScreen(
        collegeGroup = viewModel.collegeGroup.value,
        isLowerWeek = viewModel.isLowerWeek.value,
        bellSchedule = viewModel.bellSchedule.value
    )
}

@Composable
fun ScheduleScreen(
    collegeGroup: Response<CollegeGroup>,
    isLowerWeek: Response<Boolean>,
    bellSchedule: Response<BellSchedule>,

    ) {
    var isLowerWeekPreview by remember {
        mutableStateOf(false)
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
            LoadingScreen()
        }

        isFailure(collegeGroup, isLowerWeek, bellSchedule) -> {
            ErrorScreen(message = "Хмм... что-то пошло не так")
        }

        isSuccess(collegeGroup, isLowerWeek, bellSchedule) -> {
            collegeGroupData = (collegeGroup as Response.Success).data
            isLowerWeekValue = (isLowerWeek as Response.Success).data
            bellScheduleData = (bellSchedule as Response.Success).data

            //TODO make isLowerWeekPreview depends on isLowerWeekValue
            Column(Modifier.fillMaxSize()) {
                ScheduleDataSection(
                    isLowerWeekPreview = isLowerWeekPreview,
                    isLowerWeekValue = isLowerWeekValue,
                    collegeGroupData = collegeGroupData
                ) {
                    isLowerWeekPreview = !isLowerWeekPreview
                    Log.d(TAG, isLowerWeekPreview.toString())
                }

                HorizontalWeekPager { currentPage ->
                    var currentLessons = emptyList<Lesson>()

                    if (isLowerWeekPreview != null) {
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
                                imageVector = YetkIcon.LocationOn,
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
                                imageVector = YetkIcon.Person,
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
                                    imageVector = YetkIcon.ClockUpperWeek,
                                    contentDescription = "Upper week",
                                    tint = if (lesson.isCanceled) Gray50.copy(alpha = 0.38f) else Gray50
                                )
                            }

                            -1 -> {
                                Icon(
                                    imageVector = YetkIcon.ClockLowerWeek,
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
                fontWeight = FontWeight.SemiBold,
                color = Gray70
            )

            if (isLowerWeekValue != null) {
                Text(
                    text = if (isLowerWeekValue) "Нижняя неделя" else "Верхняя неделя",
                    fontFamily = Inter,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isLowerWeekValue == isLowerWeekPreview) MaterialTheme.colorScheme.secondary else Gray70
                )
            }
        }

        Text(
            text = collegeGroupData.name,
            fontFamily = Inter,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray70
        )

        if (isLowerWeekPreview != null) {
            LowerUpperWeekToggle(
                modifier = Modifier.padding(end = 12.dp),
                isLowerWeek = isLowerWeekPreview
            ) {
                onWeekToggle()
            }
        }
    }
}