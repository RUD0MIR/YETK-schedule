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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.designsystem.component.YetkCheckBox
import com.yetk.designsystem.component.YetkFilledButton
import com.yetk.designsystem.component.YetkLogoImage
import com.yetk.designsystem.component.YetkPasswordField
import com.yetk.designsystem.component.YetkTextField
import com.yetk.designsystem.theme.White
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.data.remote.viewmodel.AuthViewModel

@Composable
internal fun AuthorizationRoute(
    onNavigate: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    AuthorizationScreen(
        viewModel.rememberMeChecked,
        onCheckChange = viewModel::onCheckChange,
        loginCheck = viewModel::loginCheck,
        passwordCheck = viewModel::passwordCheck,
        onNavigate = onNavigate
    )
}

@Composable
fun AuthorizationScreen(
    rememberMeChecked: Boolean,
    onCheckChange: () -> Unit,
    loginCheck: (String) -> Boolean,
    passwordCheck: (String) -> Boolean,
    onNavigate: () -> Unit,
) {
    YetkScheduleTheme {
        var login by rememberSaveable {
            mutableStateOf("")
        }

        var password by rememberSaveable {
            mutableStateOf("")
        }

        var isLoginError by rememberSaveable {
            mutableStateOf(false)
        }

        var isPasswordError by rememberSaveable {
            mutableStateOf(false)
        }

        var passwordVisible by rememberSaveable {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
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

            YetkTextField(
                text = login,
                supportingText = if (isLoginError) "Неверный логин" else "Логин",
                "Ваш логин",
                isError = isLoginError
            ) { login = it }

            Spacer(modifier = Modifier.height(16.dp))

            YetkPasswordField(
                text = password,
                isError = isPasswordError,
                supportingText = if (isPasswordError) "Неверный пароль" else "Пароль",
                placeholderText = "Ваш пароль",
                passwordVisible = passwordVisible,
                onTextChange = { password = it }
            ) {
                passwordVisible = !passwordVisible
            }

            YetkCheckBox(value = rememberMeChecked, text = "Запомнить меня") {
                onCheckChange()
            }

            Spacer(modifier = Modifier.height(32.dp))

            YetkFilledButton(text = "Войти") {
                val loginCorrect = loginCheck(login)
                val passwordCorrect = passwordCheck(password)
                if (loginCorrect && passwordCorrect) {
                    onNavigate()
                }
                isLoginError = !loginCorrect
                isPasswordError = !passwordCorrect
            }
        }
    }
}