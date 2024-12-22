package com.example.feature.signup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmailCheckRoute(
    padding: PaddingValues = PaddingValues(),
    onShowSnackBar: (String) -> Unit = {},
    popBackStack: () -> Unit = {},
    email: String = "",
    onEmailChange: (String) -> Unit = {},
    isEmailError: Boolean = false,
    onRequestEmailVerification: () -> Unit = {}
) {

}

@Composable
fun EmailCheckScreen(
    padding: PaddingValues = PaddingValues(),
    onShowSnackBar: (String) -> Unit = {},
    popBackStack: () -> Unit = {},
    email: String = "",
    onEmailChange: (String) -> Unit = {},
    isEmailError: Boolean = false,
    onRequestEmailVerification: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BackArrow(
                onBackClick = popBackStack,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(
                    modifier = Modifier.height(48.dp)
                )
                Text(
                    text = "이메일로 회원가입",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("이메일을 입력하세요.") },
                    isError = isEmailError,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                    onClick = { onRequestEmailVerification() }
                ) {
                    Text(
                        text = "이메일 인증 요청",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}

@Composable
fun BackArrow(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Image(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "뒤로가기",
        modifier = modifier
            .size(32.dp)
            .clickable { onBackClick() },
        colorFilter = ColorFilter.tint(Color.Black)
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailCheckScreen() {
    EmailCheckScreen()
}