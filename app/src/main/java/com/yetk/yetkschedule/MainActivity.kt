package com.yetk.yetkschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yetk.yetkschedule.ui.NavHost
import com.yetk.yetkschedule.ui.theme.YetkScheduleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeworkViewModel by viewModels<HomeworkViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YetkScheduleTheme(dynamicColor = false) {
                NavHost(viewModel = homeworkViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YetkScheduleTheme {
    }
}