package com.yetk.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yetk.designsystem.R

val Inter =
    FontFamily(
        listOf(
            Font(R.font.inter_regular, FontWeight.Normal),
            Font(R.font.inter_medium, FontWeight.Medium),
            Font(R.font.inter_semi_bold, FontWeight.SemiBold),
            Font(R.font.inter_bold, FontWeight.Bold),
            Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
            Font(R.font.inter_black, FontWeight.Black)
        )
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        headlineLarge =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),
        bodyLarge =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodyMedium =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.5.sp
        ),
        bodySmall =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),
        labelLarge =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 13.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge =
        TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        )
    )
