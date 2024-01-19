package com.yetk.yetkschedule.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray80
import com.yetk.yetkschedule.ui.theme.Gray90
import com.yetk.yetkschedule.ui.theme.Inter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    @DrawableRes
    iconId: Int,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    onDismissRequest: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<String>,
    label: String = ""
) {
    Box(modifier) {
        val bodyMedium = MaterialTheme.typography.bodyMedium
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
            onValueChange = onValueChange,
            isError = isError,
            label = {
                Text(
                    text = label,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal
                )
            },
            textStyle = bodyMedium,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = Gray50
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Gray90,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedIndicatorColor = Gray80
            )
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
            list.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(
                            TextFieldValue(
                                text = text,
                                selection = TextRange(text.length)
                            )
                        )
                        onDismissRequest()
                    },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
    }
}