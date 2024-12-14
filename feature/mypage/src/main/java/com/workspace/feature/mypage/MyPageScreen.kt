package com.workspace.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MyPageRoute(
    padding: PaddingValues = PaddingValues()
) {
    MyPageScreen(padding)
}

@Composable
fun MyPageScreen(
    padding: PaddingValues = PaddingValues()
) {
    Text(
        modifier = Modifier.padding(padding),
        text = "MyPage"
    )
}