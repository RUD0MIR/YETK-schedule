package com.yetk.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.theme.YetkScheduleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YetkTopBar(
    text: String,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable () -> Unit
) {
    Column {
        TopAppBar(
            modifier = Modifier.padding(end = 16.dp),
            title = {
                Text(
                    text = text,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            navigationIcon = {
                if(navigationIcon != null)  {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = navigationIconContentDescription
                        )
                    }
                }
            },
            actions = {
                actions()
            }
        )
        YetkDivider()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DividerPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface {
            Column {
                YetkTopBar(
                    "Расписание звонков",
                    Icons.TwoTone.ArrowBack,
                    onNavigationClick = {},
                    actions = {}
                )
            }
        }
    }
}
