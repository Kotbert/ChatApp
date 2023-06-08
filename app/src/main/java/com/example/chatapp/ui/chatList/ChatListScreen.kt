package com.example.chatapp.ui.chatList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.app_ui_model.ProfileUiModel
import com.example.chatapp.ui.theme.ChatAppTheme


@Composable
fun ChatListScreen(
    uiState: ChatListViewModel.UiState, onProfileClick: () -> Unit, onRefresh: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Row {
            IconButton(
                onClick = onProfileClick
            ) {
                Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null)
            }
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                onClick = onRefresh
            ) {
                Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
            }
        }
    }) { innerPadding ->
        when {
            uiState.isLoading -> Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Список загружается")
            }

            uiState.usersList.isEmpty() -> Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Список пуст")
            }

            else -> LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(uiState.usersList) { chatItem ->
                    ChatListItem(chatItem = chatItem)
                }
            }
        }
    }
}

@Composable
private fun ChatListItem(chatItem: ProfileUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(56.dp), shape = CircleShape, color = Color.LightGray
        ) {

        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = chatItem.fullName,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Тут будет ласт сообщение",
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatScreen() {
    val chatItems = listOf(
        ProfileUiModel("John Doe", "Hello there!", ""),
        ProfileUiModel("Jane Smith", "Hi, where am i?", ""),
        ProfileUiModel("Alex Johnson", "Far from home...!", "")
    )
    ChatAppTheme {
        ChatListScreen(
            ChatListViewModel.UiState(usersList = chatItems),
            onProfileClick = {}
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyChatScreen() {
    ChatAppTheme {
        ChatListScreen(ChatListViewModel.UiState(), onProfileClick = {}) {}
    }
}
