package com.example.chatapp.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.ChatAppTheme

data class ChatMessage(
    val messageId: String,
    val senderName: String,
    val message: String,
    val timestamp: String,
    val isSentByUser: Boolean
)

@Composable
fun ChatScreen(
    chatMessagesList: List<ChatMessage>,
    onSendMessageClick: (message: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newMessage by remember { mutableStateOf("") }

    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Blue,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.LightGray,
                ),
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(onSend = { onSendMessageClick(newMessage) })
            )

            Button(
                onClick = { onSendMessageClick(newMessage) },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Send", color = Color.White)
            }
        }
    }) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 16.dp)
                .fillMaxSize()
        ) {
            items(chatMessagesList) { chatMessage ->
                ChatMessageItem(
                    message = chatMessage.message,
                    isSentByUser = chatMessage.isSentByUser,
                    timestamp = chatMessage.timestamp,
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
private fun ChatMessageItem(
    message: String, isSentByUser: Boolean, timestamp: String, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                if (isSentByUser) PaddingValues(start = 24.dp)
                else PaddingValues(end = 24.dp)
            )
            .fillMaxWidth(),
        horizontalAlignment = if (isSentByUser) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .padding(
                    if (isSentByUser) PaddingValues(start = 24.dp)
                    else PaddingValues(end = 24.dp)
                )
                .wrapContentWidth(Alignment.End), colors = CardDefaults.cardColors(
                containerColor = if (isSentByUser) Color.Blue else Color.LightGray,
                contentColor = if (isSentByUser) Color.White else Color.Black,
            ), shape = if (isSentByUser) RoundedCornerShape(
                topStart = 8.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 0.dp
            ) else RoundedCornerShape(
                topStart = 0.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 8.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = message,
                color = if (isSentByUser) Color.White else Color.Blue
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 2.dp, end = 16.dp)
                .wrapContentWidth(Alignment.End), text = timestamp, color = Color.Black
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewChatApp() {
    ChatAppTheme {
        ChatScreen(chatMessagesList = listOf(
            ChatMessage(
                messageId = "1",
                message = "asd",
                isSentByUser = true,
                senderName = "Henry",
                timestamp = "19:00"
            ),
            ChatMessage(
                messageId = "2",
                message = "qwess",
                isSentByUser = false,
                senderName = "Alexxxxxxxxxxxxxxx",
                timestamp = "19:00"
            ),
            ChatMessage(
                messageId = "1",
                message = "asd",
                isSentByUser = true,
                senderName = "Henry",
                timestamp = "19:00"
            ),
            ChatMessage(
                messageId = "2",
                message = "qwessssssssssssssssssssssssssssssss sssssssssssssssssss sssssssssssssssssssssssss",
                isSentByUser = false,
                senderName = "Alex",
                timestamp = "19:00"
            ),
            ChatMessage(
                messageId = "1",
                message = "asssssssssssssssssssssssss sssssssssssssssss ddddddddddddddddddd dddddddddddddddddd",
                isSentByUser = true,
                senderName = "Henry",
                timestamp = "19:00"
            ),
            ChatMessage(
                messageId = "2",
                message = "qwe",
                isSentByUser = false,
                senderName = "Alexxxxxxxxxxxxxxx",
                timestamp = "19:00"
            ),
        ), onSendMessageClick = {})
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewChatMessage() {
    ChatAppTheme {
        Column {
            ChatMessageItem(
                message = "Hello, how are you?", isSentByUser = false, timestamp = "19:00"
            )
            Spacer(modifier = Modifier.size(12.dp))
            ChatMessageItem(
                message = "Hello, how are you?", isSentByUser = true, timestamp = "19:01"
            )
        }
    }
}
