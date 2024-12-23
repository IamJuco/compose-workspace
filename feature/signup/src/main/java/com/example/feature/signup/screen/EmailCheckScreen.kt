package com.example.feature.signup.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.signup.SignUpViewModel
import com.workspace.core.domain.model.UiState

@Composable
fun EmailCheckRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToEmailVerification: () -> Unit = {},
    onShowSnackBar: (String) -> Unit = {},
    popBackStack: () -> Unit = {},
) {
    val verificationEmailState by viewModel.verificationEmailState.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }

    EmailCheckScreen(
        padding = padding,
        email = email,
        isEmailError = isEmailError,
        onEmailChange = {
            email = it
            isEmailError = !isValidEmail(it)
        },
        popBackStack = popBackStack,
        onRequestEmailVerification = {
            if (!isEmailError) {
                viewModel.sendVerificationEmail(email)
            } else {
                onShowSnackBar("유효하지 않은 이메일 형식입니다.")
            }
        }
    )

    if (verificationEmailState is UiState.Loading) {
        LoadingScreen()
    }

    LaunchedEffect(verificationEmailState) {
        when {
            verificationEmailState is UiState.Success -> {
                navigateToEmailVerification()
            }

            verificationEmailState is UiState.Error -> {
                val errorMessage =
                    (verificationEmailState as UiState.Error).errorMessage ?: "알 수 없는 오류가 발생했습니다."
                onShowSnackBar(errorMessage)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetTempSignUp()
        }
    }
}

@Composable
fun EmailCheckScreen(
    padding: PaddingValues = PaddingValues(),
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
                    enabled = email.isNotEmpty() && !isEmailError,
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

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$".toRegex()
    return emailRegex.matches(email)
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
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailCheckScreen() {
    EmailCheckScreen()
}