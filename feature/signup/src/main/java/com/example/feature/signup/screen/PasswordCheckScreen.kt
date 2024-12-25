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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.signup.R
import com.example.feature.signup.SignUpViewModel
import com.workspace.core.domain.model.UiState

@Composable
fun PasswordCheckRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit = {},
    onShowSnackBar: (String) -> Unit = {},
    popBackStack: () -> Unit = {}
) {
    val signUpState by viewModel.signUpState.collectAsStateWithLifecycle()
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }

    PasswordCheckScreen(
        padding = padding,
        popBackStack = popBackStack,
        password = password,
        onPasswordChange = {
            password = it
            isPasswordError = !isValidPassword(it)
        },
        confirmPassword = confirmPassword,
        onConfirmPasswordChange = { confirmPassword = it },
        onSignUp = { viewModel.signUpWithEmail(password) },
        isPasswordError = isPasswordError,
        isConfirmPasswordError = isConfirmPasswordError && isPasswordError
    )

    if (signUpState is UiState.Loading) {
        LoadingScreen()
    }

    LaunchedEffect(signUpState) {
        when {
            signUpState is UiState.Success -> {
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
fun PasswordCheckScreen(
    padding: PaddingValues = PaddingValues(),
    popBackStack: () -> Unit = {},
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUp: () -> Unit,
    isPasswordError: Boolean = false,
    isConfirmPasswordError: Boolean = false

) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            BackArrow(
                onBackClick = popBackStack,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = "비밀번호 등록",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    onValueChange = {
                        onPasswordChange(it)
                    },
                    label = { Text("비밀번호") },
                    isError = isPasswordError,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(if (isPasswordVisible) R.drawable.ic_visibility_on_24dp else R.drawable.ic_visibility_off_24dp),
                                contentDescription = if (isPasswordVisible) "비밀번호 숨기기" else "비밀번호 보기"
                            )
                        }
                    }
                )

                if (isPasswordError) {
                    Text(
                        text = "영문 대소문자, 숫자 포함 6자리 이상 입력해주세요.",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp),
                        textDecoration = TextDecoration.Underline
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = { Text("비밀번호 확인") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(if (isPasswordVisible) R.drawable.ic_visibility_on_24dp else R.drawable.ic_visibility_off_24dp),
                                contentDescription = if (isPasswordVisible) "비밀번호 숨기기" else "비밀번호 보기"
                            )
                        }
                    }
                )

//                if (isConfirmPasswordError) {
//                    Text(
//                        text = "비밀번호를 확인해 주세요.",
//                        color = Color.Red,
//                        fontSize = 12.sp,
//                        modifier = Modifier.padding(top = 4.dp),
//                        textDecoration = TextDecoration.Underline
//                    )
//                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                    onClick = { onSignUp() },
                    enabled = password == confirmPassword &&
                            !isPasswordError &&
                            password.isNotBlank() &&
                            confirmPassword.isNotBlank()
                ) {
                    Text(
                        text = "인증완료",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
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
private fun BackArrow(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Image(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "뒤로가기",
        modifier = modifier
            .size(32.dp)
            .clickable { onBackClick() },
        colorFilter = ColorFilter.tint(Color.Black)
    )
}

private fun isValidPassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}".toRegex()
    return passwordRegex.matches(password)
}