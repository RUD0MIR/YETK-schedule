package com.yetk.for_student.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yetk.designsystem.component.YetkCheckBox
import com.yetk.designsystem.component.YetkFilledButton
import com.yetk.designsystem.component.YetkLogoImage
import com.yetk.designsystem.component.YetkPasswordField
import com.yetk.designsystem.component.YetkTextField
import com.yetk.designsystem.theme.White
import com.yetk.designsystem.theme.YetkScheduleTheme

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

            YetkLogoImage(Modifier.size(height = 153.dp, width = 136.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left,
                text = "Вход",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            YetkTextField(text = login, "Логин", "Ваш логин") { login = it}

            Spacer(modifier = Modifier.height(16.dp))

            YetkPasswordField(
                text = password,
                supportingText = "Пароль",
                placeholderText = "Ваш пароль",
                passwordVisible = passwordVisible,
                onTextChange = { password = it }
            ) {
                passwordVisible = !passwordVisible
            }

            YetkCheckBox(value = rememberMeChecked, text = "Запомнить меня") {
                rememberMeChecked = !rememberMeChecked
            }

            Spacer(modifier = Modifier.height(32.dp))

            YetkFilledButton(text = "Войти") {
                //TODO show schedule screen
            }
        }
    }
}