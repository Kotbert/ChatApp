package com.example.chatapp.ui.chatList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.firebase.data_base_utils.UserTable
import com.example.chatapp.preference.Preference
import com.example.chatapp.ui.app_ui_model.ProfileUiModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val pref: Preference, private val database: FirebaseFirestore
) : ViewModel() {
    data class UiState(
        val isLoading: Boolean = false, val usersList: List<ProfileUiModel> = emptyList()
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    fun getUsersList() {
        viewModelScope.launch {
            _uiState.emit(UiState(isLoading = true))
            database.collection(UserTable.TABLE_NAME).get().addOnCompleteListener { result ->
                val currentUserID = pref.getString(UserTable.ID_KEY)
                if (result.isSuccessful) {
                    val userList = mutableListOf<ProfileUiModel>()
                    result.result.documents.forEach { documentSnapshot ->
                        if (documentSnapshot.id == currentUserID) return@forEach
                        userList.add(
                            ProfileUiModel(
                                fullName = documentSnapshot.getString(UserTable.NAME_KEY) ?: "N/A",
                                email = "Не реализовано",
                                aboutMe = "Не реализовано"
                            )
                        )
                    }
                    Log.i("TestLog", "UserList: $userList")
                    _uiState.update { it.copy(usersList = userList) }
                }
            }
            delay(1000)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    init {
        getUsersList()
    }
}