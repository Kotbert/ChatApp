package com.example.chatapp.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.firebase.data_base_utils.UserTable
import com.example.chatapp.preference.Preference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context,
    private val pref: Preference = Preference(context),
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {
    private val _authEvent = MutableStateFlow<Authentication>(Authentication.NotGetAccess)
    val authEvent get() = _authEvent.asStateFlow()
    private fun getToken() {
        FirebaseInstallations.getInstance().getToken(true).addOnSuccessListener { result ->
            updateToken(result.token)
        }
    }

    private fun updateToken(token: String) {
        val docRef = database.collection(UserTable.TABLE_NAME).document(
            pref.getString(UserTable.ID_KEY) ?: ""
        )
        docRef.update(UserTable.TOKEN, token).addOnSuccessListener {
            Toast.makeText(context, "Токен обновен", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Токен не обновлен", Toast.LENGTH_SHORT).show()
        }
    }

    fun singIn(login: String, password: String, context: Context) {
        viewModelScope.launch {
            database.collection(UserTable.TABLE_NAME).whereEqualTo(UserTable.NAME_KEY, login)
                .whereEqualTo(UserTable.PASSWORD_KEY, password).get()
                .addOnCompleteListener { result ->
                    Log.i("Register user", "Пользователь авторизован")
                    if (result.isSuccessful && result.result != null && result.result.documents.isNotEmpty()) {
                        val userResult = result.result.documents[0]
                        pref.saveBoolean(UserTable.IS_SIGNED_IN, true)
                        pref.saveString(UserTable.ID_KEY, userResult.id)
                        pref.saveString(
                            UserTable.NAME_KEY, userResult.getString(UserTable.NAME_KEY)!!
                        )
                        _authEvent.update { Authentication.GetAccess }
                        getToken()
                        Toast.makeText(context, "Вы вошли", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Не удалось войти", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun signUp(login: String, password: String) {
        viewModelScope.launch {
            val user = HashMap<String, String>()
            user[UserTable.NAME_KEY] = login
            user[UserTable.PASSWORD_KEY] = password
            database.collection(UserTable.TABLE_NAME).add(user).addOnSuccessListener { result ->
                pref.saveBoolean(UserTable.IS_SIGNED_IN, true)
                pref.saveString(UserTable.ID_KEY, result.id)
                pref.saveString(UserTable.NAME_KEY, login)
                Log.i("Register user", "Пользователь зарегестрирован")
                _authEvent.update { Authentication.GetAccess }
            }.addOnFailureListener { result ->
                Log.i("Register user", "Пользователь не зарегестрирован")
            }
        }
    }
}

sealed class Authentication {
    object GetAccess : Authentication()
    object NotGetAccess : Authentication()
}