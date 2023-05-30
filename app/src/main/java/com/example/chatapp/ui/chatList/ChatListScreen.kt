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
import com.example.chatapp.ui.app_ui_model.ChatItem
import com.example.chatapp.ui.theme.ChatAppTheme


@Composable
fun ChatListScreen(chatItems: List<ChatItem>, onProfileClick: () -> Unit) {
    when (chatItems) {
        emptyList<ChatItem>() -> Scaffold(topBar = {
            Row {
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),
                    onClick = onProfileClick
                ) {
                    Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null)
                }
            }
        }) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Список пуст")
            }
        }

        else -> Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            Row {
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),
                    onClick = onProfileClick
                ) {
                    Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null)
                }
            }
        }) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(chatItems) { chatItem ->
                    ChatListItem(chatItem = chatItem)
                }
            }
        }
    }
}

@Composable
private fun ChatListItem(chatItem: ChatItem) {
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
                text = chatItem.senderName,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chatItem.message,
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
private fun PreviewChatScreen() {
    val chatItems = listOf(
        ChatItem("John Doe", "Hello there!", Icons.Rounded.AccountCircle),
        ChatItem("Jane Smith", "Hi, where am i?", Icons.Rounded.AccountCircle),
        ChatItem("Alex Johnson", "Far from home...!", Icons.Rounded.AccountCircle)
    )
    ChatAppTheme {
        ChatListScreen(chatItems = chatItems, onProfileClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyChatScreen() {
    ChatAppTheme {
        ChatListScreen(chatItems = emptyList(), onProfileClick = {})
    }
}
