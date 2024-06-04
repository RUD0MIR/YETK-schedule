package com.yetk.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.icon.YetkIcon

@Composable
fun LowerUpperWeekToggle(modifier: Modifier, isLowerWeek: Boolean, onValueChange: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = { onValueChange() }
    ) {
        if (!isLowerWeek) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = YetkIcon.ToLowerWeek),
                contentDescription = "To lower week",
            )
        } else {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = YetkIcon.ToUpperWeek),
                contentDescription = "To upper week",
            )
        }
    }
}

@Composable
fun YetkExpandToggle(modifier: Modifier = Modifier, expanded: Boolean, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = if (expanded) "Close" else "Expand"
        )
    }
}