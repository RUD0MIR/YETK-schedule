package com.yetk.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.Gray70
import com.yetk.designsystem.theme.White

@Composable
fun YetkTextField(
    text: String,
    supportingText: String = "",
    placeholderText: String = "",
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onTextChange(it) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = White,
            unfocusedIndicatorColor = Gray70
        ),
        supportingText = {
            Text(text = supportingText, color = Gray50)
        },
        placeholder = {
            Text(text = placeholderText, color = Gray70, fontWeight = FontWeight.Medium)
        },
    )
}

@Composable
fun YetkPasswordField(
    text: String,
    supportingText: String = "",
    placeholderText: String = "",
    passwordVisible: Boolean,
    onTextChange: (String) -> Unit,
    onIconClick: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onTextChange(it) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = White,
            unfocusedIndicatorColor = Gray70
        ),
        supportingText = {
            Text(text = supportingText, color = Gray50)
        },
        placeholder = {
            Text(text = placeholderText, color = Gray70, fontWeight = FontWeight.Medium)
        },
        trailingIcon = {
            val icon = if (passwordVisible)
                YetkIcon.VisibilityOff
            else YetkIcon.Visibility

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = onIconClick){
                Icon(imageVector = icon, description)
            }
        }
    )
}

@Preview
@Composable
fun TextFieldsPreview() {
    YetkTextField("test") {}
}
