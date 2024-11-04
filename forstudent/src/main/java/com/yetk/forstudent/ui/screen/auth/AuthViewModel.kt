package com.yetk.forstudent.ui.screen.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.yetk.forstudent.domain.repository.CollegeGroupDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val repository: CollegeGroupDataRepository
) : ViewModel() {
    var rememberMeChecked by mutableStateOf(false)
        private set

    var login by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoginError by mutableStateOf(false)
        private set

    var isPasswordError by mutableStateOf(false)
        private set

    fun changeLogin(txt: String) {
        login = txt
    }

    fun changePassword(txt: String) {
        password = txt
    }

    fun onCheckChange() {
        rememberMeChecked = !rememberMeChecked

        if (rememberMeChecked) {
            // TODO save in datastore
        }
    }

    fun authenticated(): Boolean {
        if (login.isBlank() || password.isBlank()) return false

        if (!fakeLoginCheck(password)) return false

        if (!fakePasswordCheck(password)) return false

        return true
    }

    private fun fakeLoginCheck(password: String): Boolean {
        return if (password == "20kis-1") {
            true
        } else {
            isLoginError = true
            false
        }
    }

    private fun fakePasswordCheck(login: String): Boolean {
        return if (login == "1234") {
            true
        } else {
            isPasswordError = true
            false
        }
    }
}
