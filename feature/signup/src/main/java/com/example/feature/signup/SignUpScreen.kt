package com.example.feature.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.signup.screen.EmailCheckScreen
import com.workspace.core.domain.model.UiState

/**
@Composable
fun SignUpRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit = {},
    onShowSnackBar: (String) -> Unit = {}
) {
    val signUpState by viewModel.signUpState.collectAsStateWithLifecycle()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    if (signUpState is UiState.Loading) {
        LoadingScreen()
    }

    EmailCheckScreen(
        padding = padding,

    )

    LaunchedEffect(signUpState) {
        when {
            signUpState is UiState.Success -> {
                onShowSnackBar("회원가입이 완료되었습니다.")
                navigateToLogin()
            }


            signUpState is UiState.Error -> {
                val errorMessage =
                    (signUpState as UiState.Error).errorMessage ?: "알 수 없는 오류가 발생했습니다."
                onShowSnackBar(errorMessage)
            }
        }
    }
}

@Composable
fun SignUpScreen(
    padding: PaddingValues,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    isConfirmPasswordError: Boolean,
    isButtonEnabled: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = { Text("이메일") },
            isError = isEmailError,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            isError = isPasswordError,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text("비밀번호 확인") },
            visualTransformation = PasswordVisualTransformation(),
            isError = isConfirmPasswordError,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
            enabled = isButtonEnabled,
            onClick = onSignUpClick
        ) {
            Text(
                text = "회원가입",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
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

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$".toRegex()
    return emailRegex.matches(email)
}

private fun isPasswordComplex(password: String): Boolean {
    val passwordRegex = "^(?=.*[a-zA-Z]).{8,}$".toRegex()
    return passwordRegex.matches(password)
}

 */