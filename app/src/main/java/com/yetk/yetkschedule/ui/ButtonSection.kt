package com.yetk.yetkschedule.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.ui.theme.Green
import com.yetk.yetkschedule.ui.theme.White

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        FilledTonalButton(
            modifier = Modifier.padding(start = 32.dp, end = 16.dp, bottom = 32.dp),
            onClick = {
                onPositiveButtonClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        ) {
            Text(
                text = positiveButtonText,
                style = MaterialTheme.typography.labelLarge,
                color = White
            )
        }

        OutlinedButton(
            onClick = {
                onNegativeButtonClick()
            }
        ) {
            Text(
                text = negativeButtonText,
                style = MaterialTheme.typography.labelLarge,
                color = Green
            )
        }
    }
}