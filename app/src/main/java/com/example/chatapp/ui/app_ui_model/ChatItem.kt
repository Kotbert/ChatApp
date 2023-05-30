package com.example.chatapp.ui.app_ui_model

import androidx.compose.ui.graphics.vector.ImageVector

data class ChatItem(
    val senderName: String, val message: String, val imageResId: ImageVector
)
