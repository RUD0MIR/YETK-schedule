package com.yetk.yetkschedule.other

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val title: String,
    val icon: Painter,
    val route: String
)