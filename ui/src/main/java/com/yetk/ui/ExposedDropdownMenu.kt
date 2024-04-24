package com.yetk.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray80
import com.yetk.yetkschedule.ui.theme.Gray90
import com.yetk.yetkschedule.ui.theme.Green
import com.yetk.yetkschedule.ui.theme.Inter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenu(
    modifier: Modifier = Modifier,
    value: String,
    @DrawableRes
    iconId: Int,
    onExpandedChange: () -> Unit,
    onDismissRequest: () -> Unit,
    onMenuItemClick: (selectionOption: String) -> Unit,
    dropDownExpanded: Boolean,
    options: List<String>,
    label: String = ""
) {
    val bodyMedium = MaterialTheme.typography.bodyMedium

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = dropDownExpanded,
        onExpandedChange = {
            onExpandedChange()
        }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = value,
            onValueChange = { },
            label = { Text(label, fontFamily = Inter, fontWeight = FontWeight.Normal) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = Gray50
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = dropDownExpanded
                )
            },
            textStyle = bodyMedium,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Gray90,
                cursorColor = Green,
                focusedIndicatorColor = Green,
                focusedLabelColor = Green,
                unfocusedIndicatorColor = Gray80
            )
        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = dropDownExpanded,
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onMenuItemClick(selectionOption)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}