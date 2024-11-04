package com.yetk.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.yetk.designsystem.R
import com.yetk.designsystem.theme.YetkScheduleTheme

@Composable
fun YetkLogoImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.yetk_logo),
        contentDescription = "УЭТК СГУ"
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DividerPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface {
            Column {
                YetkLogoImage()
            }
        }
    }
}
