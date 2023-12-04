package com.example.lessonsschedulemanagerv2.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.R
import com.example.lessonsschedulemanagerv2.data.local.model.Lesson
import com.yetk.yetkschedule.other.Constants.MON
import com.yetk.yetkschedule.other.NoRippleInteractionSource
import com.yetk.yetkschedule.other.SwipeableSnackbarHost
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
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import kotlinx.coroutines.launch

val monItems = mutableListOf(
    Lesson(
        id = 2,
        subject = "Экономика отрасли",
        room = "302м",
        teacher = "Петров И.И.",
        startTime = "11:20",
        endTime = "12:50",
        weekState = WeekState.UPPER_WEEK,
        dayOfWeek = MON,
        hasHomework = true,
        isDisabled = false
    ),

    Lesson(
        id = 3,
        subject = "Физкультура",
        room = "302",
        teacher = "Иванов Е.И.",
        startTime = "13:20",
        endTime = "14:50",
        weekState = WeekState.EVERY_WEEK,
        dayOfWeek = MON,
        hasHomework = false,
        isDisabled = false
    ),
    Lesson(
        id = 0,
        subject = "Информационные технологии",
        room = "401м",
        teacher = "Андреева А.А.",
        startTime = "15:00",
        endTime = "16:30",
        weekState = WeekState.EVERY_WEEK,
        dayOfWeek = MON,
        hasHomework = true,
        isDisabled = false
    ),
    Lesson(
        id = 1,
        subject = "Внедрение и поддержка КС",
        room = "201",
        teacher = "Петров И.И.",
        startTime = "16:40",
        endTime = "18:10",
        weekState = WeekState.EVERY_WEEK,
        dayOfWeek = MON,
        hasHomework = false,
        isDisabled = false
    ),
)
val tueItems = mutableListOf(
    Lesson(
        id = 3,
        subject = "Физкультура",
        room = "302",
        teacher = "Иванов Е.И.",
        startTime = "13:20",
        endTime = "14:50",
        weekState = WeekState.EVERY_WEEK,
        dayOfWeek = MON,
        hasHomework = false,
        isDisabled = false
    ),
    Lesson(
        id = 3,
        subject = "Физкультура",
        room = "302",
        teacher = "Иванов Е.И.",
        startTime = "15:00",
        endTime = "16:30",
        weekState = WeekState.EVERY_WEEK,
        dayOfWeek = MON,
        hasHomework = false,
        isDisabled = false
    ),
)
val wedItems = mutableListOf<Lesson>()
val thuItems = mutableListOf<Lesson>()
val friItems = mutableListOf<Lesson>()
val satItems = mutableListOf<Lesson>()
val lessonListItems = mutableListOf(
    monItems,
    tueItems,
    wedItems,
    thuItems,
    friItems,
    satItems
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    bottomBarPadding: PaddingValues,
    onNavigateToDetailScreen: (id: Int?) -> Unit,
) {
    var isUpperWeek by remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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
                    IconButton(onClick = { isUpperWeek = !isUpperWeek }) {
                        if (isUpperWeek) {
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
        snackbarHost = {
            SwipeableSnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToDetailScreen(-1)
                },
                containerColor = White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add",
                    tint = Gray50
                )
            }
        },
    ) { topBarPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding)
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp), thickness = 1.dp, color = Gray90
            )

            HorizontalWeekPager() { currentPage ->
                val currentLessons = lessonListItems[currentPage]
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(count = currentLessons.size) {
//
//                    }
//                }

                DragDropList(
                    modifier = Modifier.fillMaxSize(),
                    dataItems = currentLessons,
                    onMove = { fromIndex, toIndex -> currentLessons.move(fromIndex, toIndex) }
                ) { dataItem ->
                    Column() {
                        LessonListItem(
                            dataItem,
                            onItemClick = { lessonId ->
                                onNavigateToDetailScreen(lessonId)
                            },
                            onBackgroundEndClick = { id ->
                                //TODO hide lesson
                                //TODO make slide action close if other slide action is opened
                                scope.launch {
                                    val result = snackbarHostState
                                        .showSnackbar(
                                            message = "Lesson deleted.",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                    when (result) {
                                        SnackbarResult.ActionPerformed -> {
                                            //TODO show lesson
                                        }

                                        SnackbarResult.Dismissed -> {
                                            //TODO delete lesson
                                        }
                                    }
                                }
                            }
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
                    color = if (isSelected) Green else Gray70,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LessonListItem(
    lesson: Lesson,
    onItemClick: (id: Int) -> Unit,
    onBackgroundEndClick: (id: Int) -> Unit
) {
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
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                tint = White
            )
        },
        onBackgroundEndClick = {
            onBackgroundEndClick(lesson.id)
        }
    ) { shape ->
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = if (lesson.isDisabled) WhiteDisabled else White
            ),
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        onItemClick(lesson.id)
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
                            if(lesson.hasHomework) {
                                Icon(
                                    modifier = Modifier.padding(end = 8.dp),
                                    painter = painterResource(id = R.drawable.ic_home),
                                    contentDescription = "Has homework",
                                    tint = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
                                )
                            }


                            when (lesson.weekState) {
                                WeekState.EVERY_WEEK -> {}
                                WeekState.UPPER_WEEK -> {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_clock_upper_week),
                                        contentDescription = "Upper week",
                                        tint = if (lesson.isDisabled) Gray50.copy(alpha = 0.38f) else Gray50
                                    )
                                }

                                WeekState.LOWER_WEEK -> {
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
}