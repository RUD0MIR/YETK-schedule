package com.yetk.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.theme.YetkScheduleTheme

@Composable
fun YetkDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        thickness = 1.dp,
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DividerPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface {
            Column {
                Divider()
            }
        }
    }
}