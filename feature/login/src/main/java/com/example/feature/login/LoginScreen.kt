package com.example.feature.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginRoute(
    padding: PaddingValues = PaddingValues()
) {
    LoginScreen(padding)
}

@Composable
fun LoginScreen(
    padding: PaddingValues
) {
    Text(
        modifier = Modifier.padding(padding),
        text = "로그인"
    )
}