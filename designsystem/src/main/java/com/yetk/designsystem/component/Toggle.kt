package com.yetk.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.YetkScheduleTheme

@Composable
fun LowerUpperWeekToggle(
    modifier: Modifier,
    contentDescription: String? = null,
    isLowerWeek: Boolean,
    onValueChange: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onValueChange() }
    ) {
        if (!isLowerWeek) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = YetkIcon.ToLowerWeek),
                contentDescription = contentDescription,
            )
        } else {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = YetkIcon.ToUpperWeek),
                contentDescription = contentDescription,
            )
        }
    }
}

@Composable
fun YetkExpandToggle(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = contentDescription
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DividerPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface {
            Column {
                var isLowerWeek by remember {
                    mutableStateOf(true)
                }
                LowerUpperWeekToggle(modifier = Modifier, isLowerWeek = isLowerWeek) {
                    isLowerWeek = !isLowerWeek
                }


                var expanded by remember {
                    mutableStateOf(true)
                }
                YetkExpandToggle(expanded = expanded) {
                    expanded = !expanded
                }
            }
        }
    }
}
