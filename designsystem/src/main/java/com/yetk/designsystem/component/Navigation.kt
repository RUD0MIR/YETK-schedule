package com.yetk.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.YetkScheduleTheme

@Composable
fun RowScope.YetkNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors =
        NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun YetkNavigationBar(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
        content = content
    )
}

private enum class PreviewTopLevelDestination(
    val icon: ImageVector,
    val text: String
) {
    SCHEDULE(
        icon = YetkIcon.Schedule,
        text = "Расписание"
    ),
    HOMEWORK(
        icon = YetkIcon.Homework,
        text = "Домашнее задание"
    ),
    BELLS(
        icon = YetkIcon.BellSchedule,
        text = "Расписание звонков"
    ),
    SUBJECTS(
        icon = YetkIcon.Subjects,
        text = "Предметы"
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DividerPreview() {
    val destinations =
        listOf(
            PreviewTopLevelDestination.SCHEDULE,
            PreviewTopLevelDestination.HOMEWORK,
            PreviewTopLevelDestination.BELLS,
            PreviewTopLevelDestination.SUBJECTS
        )

    YetkScheduleTheme(dynamicColor = false) {
        Surface {
            Column {
                YetkNavigationBar {
                    destinations.forEach { destination ->
                        var selected by remember {
                            mutableStateOf(false)
                        }
                        YetkNavigationBarItem(
                            selected = selected,
                            onClick = { selected = !selected },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = destination.icon,
                                    contentDescription = null
                                )
                            },
                            label = {},
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}
