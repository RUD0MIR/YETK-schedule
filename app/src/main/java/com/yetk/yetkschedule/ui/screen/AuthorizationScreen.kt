package com.yetk.yetkschedule.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yetk.yetkschedule.R
import com.yetk.yetkschedule.ui.theme.Blue
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray70
import com.yetk.yetkschedule.ui.theme.Gray80
import com.yetk.yetkschedule.ui.theme.Inter
import com.yetk.yetkschedule.ui.theme.White
import com.yetk.yetkschedule.ui.theme.YetkScheduleTheme

@Preview
@Composable
fun AuthorizationScreen() {
    YetkScheduleTheme {
        var login by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        var passwordVisible by remember {
            mutableStateOf(false)
        }

        var rememberMeChecked by remember {
            mutableStateOf(false)
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Image(
                modifier = Modifier.size(height = 153.dp, width = 136.dp),
                painter = painterResource(id = R.drawable.yetk_logo),
                contentDescription = "УЭТК СГУ"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left,
                text = "Вход",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = login,
                onValueChange = { login = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Blue,
                    focusedIndicatorColor = Blue,
                    unfocusedContainerColor = White,
                    unfocusedIndicatorColor = Gray70
                ),
                supportingText = {
                    Text(text = "Логин", color = Gray50)
                },
                placeholder = {
                    Text(text = "Ваш логин", color = Gray70, fontWeight = FontWeight.Medium)
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { login = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Blue,
                    focusedIndicatorColor = Blue,
                    unfocusedContainerColor = White,
                    unfocusedIndicatorColor = Gray70
                ),
                supportingText = {
                    Text(text = "Пароль", color = Gray50)
                },
                placeholder = {
                    Text(text = "Ваш пароль", color = Gray70, fontWeight = FontWeight.Medium)
                },
                trailingIcon = {
                    val icon = if (passwordVisible)
                        R.drawable.ic_hide
                    else R.drawable.ic_show

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(painter = painterResource(id = icon), description)
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = rememberMeChecked,
                    onCheckedChange = { rememberMeChecked = !rememberMeChecked },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Blue,
                        uncheckedColor = Gray50
                    )
                )

                Text(
                    text = "Запомнить меня",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray50
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    //TODO show schedule screen
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                Text(text = "Войти", color = White)
            }
        }
    }
}