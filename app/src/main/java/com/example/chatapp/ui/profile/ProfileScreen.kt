package com.example.chatapp.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.ui.app_ui_model.ProfileUiModel

@Composable
fun ProfileScreen(
    uiState: ProfileViewModel.UiState,
    onBackClick: () -> Unit,
    onSignOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(modifier = modifier, topBar = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(modifier = Modifier.padding(8.dp), onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = null
                )
            }
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                onClick = onSignOutClick
            ) {
                Icon(imageVector = Icons.Rounded.ExitToApp, contentDescription = null)
            }
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.aspectRatio(1f)) {
                Image(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Profile",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileInfoItem(
                label = "Username", value = uiState.profileFields.fullName
            )
            ProfileInfoItem(
                label = "Email", value = uiState.profileFields.email
            )
            ProfileInfoItem(
                label = "AboutMe", value = uiState.profileFields.aboutMe
            )
        }
    }
}


@Composable
fun ProfileInfoItem(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(139, 221, 255, 255))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label, style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White
                ), modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value, style = TextStyle(
                    fontSize = 18.sp, color = Color.White
                ), modifier = Modifier.fillMaxWidth()
            )
        }
        Divider(color = Color.DarkGray.copy(alpha = .4f))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileScreen() {
    ProfileScreen(uiState = ProfileViewModel.UiState(
        profileFields = ProfileUiModel(
            fullName = "JohnDoe",
            email = "johndoe@example.com",
            aboutMe = "I'm a software developer."
        )
    ), onBackClick = {}, onSignOutClick = {})
}
