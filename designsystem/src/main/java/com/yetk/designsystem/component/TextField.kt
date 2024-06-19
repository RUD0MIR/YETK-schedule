package com.yetk.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.yetk.designsystem.theme.Inter
import com.yetk.designsystem.theme.YetkScheduleTheme

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
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        ),
        supportingText = {
            Text(text = supportingText)
        },
        placeholder = {
            Text(text = placeholderText, fontWeight = FontWeight.Medium)
        },
    )
}

@Composable
fun YetkPasswordField(
    text: String,
    supportingText: String = "",
    placeholderText: String = "",
    passwordVisible: Boolean,
    showIconContentDescription: String? = null,
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
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        ),
        supportingText = {
            Text(text = supportingText)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        singleLine = true,
        placeholder = {
            Text(text = placeholderText, fontWeight = FontWeight.Medium)
        },
        trailingIcon = {
            val icon = if (passwordVisible)
                YetkIcon.VisibilityOff
            else YetkIcon.Visibility

            IconButton(onClick = onIconClick){
                Icon(imageVector = icon, showIconContentDescription)
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
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
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
                text = placeholderText
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TextFieldPreview() {
    YetkScheduleTheme(dynamicColor = false) {
        Surface {
            Column {
                YetkTextField("supporting text", "placeholder text") { }

                Spacer(modifier = Modifier.height(16.dp))

                YetkTextField(
                    "text",
                    "supporting text",
                    "placeholder text",
                    isError = true
                ) { }

                Spacer(modifier = Modifier.height(16.dp))

                var passwordVisible by remember {
                    mutableStateOf(false)
                }
                YetkPasswordField(
                    text = "text",
                    supportingText = "supportingText",
                    passwordVisible = passwordVisible,
                    onTextChange = {},
                    onIconClick = {passwordVisible = !passwordVisible}
                )

            }
        }
    }
}
