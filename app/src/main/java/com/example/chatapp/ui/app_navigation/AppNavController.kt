package com.example.chatapp.ui.app_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatapp.ui.chatList.ChatListScreen
import com.example.chatapp.ui.chatList.ChatListViewModel
import com.example.chatapp.ui.login.Authentication.GetAccess
import com.example.chatapp.ui.login.Authentication.NotGetAccess
import com.example.chatapp.ui.login.LoginScreen
import com.example.chatapp.ui.login.LoginViewModel
import com.example.chatapp.ui.profile.ProfileScreen
import com.example.chatapp.ui.profile.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatApp(navController: NavHostController) {
    NavHost(navController, startDestination = ChatScreens.LOGIN_SCREEN) {
        composable(ChatScreens.LOGIN_SCREEN) {
            val viewModel: LoginViewModel = koinViewModel()
            val context = LocalContext.current
            val authentication by viewModel.authEvent.collectAsStateWithLifecycle(NotGetAccess)
            DisposableEffect(authentication) {
                onDispose {
                    when (authentication) {
                        GetAccess -> navController.navigate(ChatScreens.CHATS_LIST)
                        NotGetAccess -> {}
                    }
                }
            }

            LoginScreen(onLoginClicked = { login, password ->
                viewModel.singIn(
                    login, password, context
                )
            }, onCreteAccount = { username, password ->
                viewModel.signUp(
                    login = username, password = password
                )
                navController.navigate(ChatScreens.CHATS_LIST)
            })
        }
        composable(ChatScreens.CHATS_LIST) {
            val viewModel: ChatListViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ChatListScreen(
                uiState = uiState,
                onProfileClick = { navController.navigate(ChatScreens.PROFILE_SCREEN) }
            ) { viewModel.getUsersList() }
        }
        composable(ChatScreens.CHAT_MESSAGE) {}
        composable(ChatScreens.PROFILE_SCREEN) {
            val viewModel: ProfileViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val authentication by viewModel.authEvent.collectAsStateWithLifecycle(GetAccess)
            DisposableEffect(authentication) {
                onDispose {
                    when (authentication) {
                        GetAccess -> {}
                        NotGetAccess -> navController.navigate(ChatScreens.LOGIN_SCREEN)
                    }
                }
            }

            ProfileScreen(uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onSignOutClick = { viewModel.sinOut() })
        }
    }
}

private object ChatScreens {
    const val LOGIN_SCREEN = "login_screen"
    const val CHATS_LIST = "chat_list_screen"
    const val CHAT_MESSAGE = "chat_screen"
    const val PROFILE_SCREEN = "profile_screen"
}