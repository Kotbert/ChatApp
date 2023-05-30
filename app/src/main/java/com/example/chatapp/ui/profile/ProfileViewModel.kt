package com.example.chatapp.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.firebase.data_base_utils.UserTable
import com.example.chatapp.preference.Preference
import com.example.chatapp.ui.app_ui_model.ProfileUiModel
import com.example.chatapp.ui.login.Authentication
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(context: Context, private val pref: Preference = Preference(context)) :
    ViewModel() {
    private val _authEvent = MutableStateFlow<Authentication>(Authentication.GetAccess)
    val authEvent get() = _authEvent.asStateFlow()

    data class UiState(
        val profileFields: ProfileUiModel = ProfileUiModel(),
        val isLoading: Boolean = false,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private fun getProfileDat() {
        viewModelScope.launch {
            _uiState.emit(UiState(isLoading = true))
            val data = ProfileUiModel(
                fullName = pref.getString(UserTable.NAME_KEY) ?: "N/A",
                aboutMe = "Не реализовано",
                email = "Не реализовано"
            )
            _uiState.update { it.copy(isLoading = false, profileFields = data) }
        }
    }

    fun sinOut() {
        FirebaseFirestore.getInstance().collection(UserTable.TABLE_NAME)
            .document(pref.getString(UserTable.ID_KEY) ?: "")
            .update(UserTable.TOKEN, FieldValue.delete())
            .addOnSuccessListener { _authEvent.update { Authentication.NotGetAccess } }
    }

    init {
        getProfileDat()
    }
}