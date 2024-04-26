package com.yetk.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.White

@Composable
fun YetkFilledButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(text = text, color = White)
    }
}

@Composable
fun YetkOutlinedButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier,
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun YetkAddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
        containerColor = White
    ) {
        Icon(
            imageVector = YetkIcon.Add,
            contentDescription = "Add",
            tint = Gray50
        )
    }
}
