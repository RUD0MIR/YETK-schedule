package com.yetk.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.theme.Gray90

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YetkTopBar(text: String) {
    Column() {
        TopAppBar(
            modifier = Modifier.padding(end = 16.dp),
            title = {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
            },
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp), thickness = 1.dp, color = Gray90
        )
    }
}