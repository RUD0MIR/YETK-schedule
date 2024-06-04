package com.yetk.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlyTextTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: @Composable() (() -> Unit) = {},
    onValueChange: (newValue: String) -> Unit
) {
    val bodyMedium = MaterialTheme.typography.bodyMedium
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.8f),
        value = value,
        textStyle = bodyMedium,
        placeholder = {
            placeholder()
        },
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            imeAction = ImeAction.Done
        ),
        maxLines = 3,
    )
}