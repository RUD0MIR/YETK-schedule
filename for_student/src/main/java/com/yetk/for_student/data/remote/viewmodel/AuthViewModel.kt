package com.yetk.for_student.data.remote.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.yetk.for_student.data.remote.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: FirestoreRepository,
) : ViewModel() {
    var rememberMeChecked by mutableStateOf(false)
        private set

    fun onCheckChange() {
        rememberMeChecked = !rememberMeChecked

        if (rememberMeChecked) {
            //TODO save in datastore
        }
    }
    fun passwordCheck(login: String): Boolean {
        return login == "11"
    }

    fun loginCheck(password: String): Boolean {
        return password == "11"
    }
}


