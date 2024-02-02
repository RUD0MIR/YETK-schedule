package com.yetk.yetkschedule.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingScreen(modifier: Modifier = Modifier, topBarTitle: String) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TopAppBar(
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.TopCenter),
            title = {
                Text(
                    text = topBarTitle,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        )
        ProgressBar(modifier = Modifier.align(Alignment.Center))
    }
}