package com.yetk.yetkschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.StudentApp
import dagger.hilt.android.AndroidEntryPoint

//TODO fix colors on authScreen and bottomBar

//TODO remove sealed class pattern from HomeworkViewModel
//TODO add StateHandle to HomeworkViewmodel and get navArgs for detail screen
private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YetkScheduleTheme(dynamicColor = false) {
                StudentApp()
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