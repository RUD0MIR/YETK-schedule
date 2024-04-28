package com.yetk.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yetk.designsystem.R

@Composable
fun YetkLogoImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.yetk_logo),
        contentDescription = "УЭТК СГУ"
    )
}