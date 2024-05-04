package com.yetk.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.Gray70
import com.yetk.designsystem.theme.Inter
import com.yetk.designsystem.theme.White

@Composable
fun YetkTextField(
    text: String,
    supportingText: String = "",
    placeholderText: String = "",
    isError: Boolean = false,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        isError = isError,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
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
    isError: Boolean = false,
    onTextChange: (String) -> Unit,
    onIconClick: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        isError = isError,
        onValueChange = { onTextChange(it) },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = White,
            unfocusedIndicatorColor = Gray70
        ),
        supportingText = {
            Text(text = supportingText, color = Gray50)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        singleLine = true,
        placeholder = {
            Text(text = placeholderText, color = Gray70, fontWeight = FontWeight.Medium)
        },
        trailingIcon = {
            val icon = if (passwordVisible)
                YetkIcon.VisibilityOff
            else YetkIcon.Visibility

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = onIconClick){
                Icon(imageVector = icon, description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YetkAutocompleteTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    label: String,
    dropDownExpanded: Boolean,
    dropDownMenuItems: List<String>,
    onValueChange: (TextFieldValue) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Box(modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused)
                        onDismissRequest()
                },
            value = value,
            onValueChange = { onValueChange(it) },
            label = {
                Text(
                    text = label,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = White,
                unfocusedIndicatorColor = Gray70
            ),
        )

        DropdownMenu(
            expanded = dropDownExpanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest
        ) {
            dropDownMenuItems.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(
                            TextFieldValue(
                                text = text,
                                selection = TextRange(text.length)
                            )
                        )
                    },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
    }
}

@Composable
fun YetkMultilineTextField(modifier: Modifier = Modifier, value: String, placeholderText: String = "", onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholderText,
                color = Gray70
            )
        },
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
        ),
    )
}

@Preview
@Composable
fun TextFieldsPreview() {
    YetkTextField("test") {}
}
