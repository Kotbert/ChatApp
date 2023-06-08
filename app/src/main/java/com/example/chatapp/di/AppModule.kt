package com.example.chatapp.di

import android.content.Context
import com.example.chatapp.preference.Preference
import com.example.chatapp.ui.chatList.ChatListViewModel
import com.example.chatapp.ui.login.LoginViewModel
import com.example.chatapp.ui.profile.ProfileViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModule {
    val appModule = module {
        // Define your dependencies here
        single { providePreference(androidContext().applicationContext) }
        single { provideFireBase() }
        factory { } // Factory dependency

        viewModelOf(::LoginViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::ChatListViewModel)
    }


    private fun providePreference(context: Context) = Preference(context)
    private fun provideFireBase() = FirebaseFirestore.getInstance()
}