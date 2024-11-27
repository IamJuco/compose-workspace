package com.workspace.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState).padding(10.dp)
    ) {
        Text(
            text = "Home",
            fontSize = 50.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
        Text(
            text = "Home",
            fontSize = 20.sp
        )
    }
}