package com.example.feature.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.workspace.core.domain.model.ServiceResult

@Composable
fun LoginRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginWithEmailState.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    when (loginState) {
        null -> LoginScreen(
            padding = padding,
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            onLoginClick = { viewModel.loginWithEmail(email, password) }
        )
        is ServiceResult.Loading -> LoadingScreen()
        is ServiceResult.Success -> LoginScreen(
            padding = padding,
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            onLoginClick = { viewModel.loginWithEmail(email, password) }
        )
        is ServiceResult.Error -> {
            LoginScreen(
                padding = padding,
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                onLoginClick = { viewModel.loginWithEmail(email, password) }
            )
            Log.d("0526", (loginState as ServiceResult.Error).error.message)
        }
    }
}

@Composable
fun LoginScreen(
    padding: PaddingValues,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        LoginTitleCard()
        LoginFieldCard(
            email = email,
            onEmailChange = onEmailChange,
            password = password,
            onPasswordChange = onPasswordChange
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(64.dp),
            onClick = { onLoginClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {
            Text(
                text = "로그인",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "아이디 찾기",
                fontSize = 16.sp
            )
            Text(
                text = "ㅣ",
                fontSize = 16.sp
            )
            Text(
                text = "비밀번호 찾기",
                fontSize = 16.sp
            )
            Text(
                text = "ㅣ",
                fontSize = 16.sp
            )
            Text(
                text = "회원가입",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun LoginTitleCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Magenta)) {
                    append("C")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("ompose")
                }
            },
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            fontSize = 38.sp
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("Work")
                }
                withStyle(style = SpanStyle(color = Color.Magenta)) {
                    append("space")
                }
            },
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            fontSize = 38.sp
        )
    }
}

@Composable
fun LoginFieldCard(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = RectangleShape
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text("이메일") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text("비밀번호") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(if (isPasswordVisible) R.drawable.ic_visibility_on_24dp else R.drawable.ic_visibility_off_24dp),
                        contentDescription = if (isPasswordVisible) "비밀번호 숨기기" else "비밀번호 보기"
                    )
                }
            },
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = "다시 시도")
        }
    }
}

@Composable
fun GoogleLoginCard() {

}

//@Composable
//@Preview(showBackground = true)
//fun PreviewLoginScreen() {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    LoginScreen(
//        padding = PaddingValues(),
//        email = email,
//        onEmailChange = { email = it },
//        password = password,
//        onPasswordChange = { password = it }
//    )
//}