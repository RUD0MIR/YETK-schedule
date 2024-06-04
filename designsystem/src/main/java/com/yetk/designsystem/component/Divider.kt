package com.yetk.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YetkDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        thickness = 1.dp,
    )
}