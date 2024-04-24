package com.yetk.model

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val title: String,
    val icon: Painter,
    val route: String
)