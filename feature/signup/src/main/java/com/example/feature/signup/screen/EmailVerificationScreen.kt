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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.signup.SignUpViewModel
import com.workspace.core.domain.model.UiState

@Composable
fun EmailVerificationRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToPasswordCheck: () -> Unit = {},
    onShowSnackBar: (String) -> Unit = {},
    popBackStack: () -> Unit = {},
) {
    val isEmailVerifiedState by viewModel.isEmailVerifiedState.collectAsStateWithLifecycle()

    EmailVerificationScreen(
        padding = padding,
        popBackStack = popBackStack,
        onCheckEmailVerified = { viewModel.checkEmailVerified() }
    )

    if (isEmailVerifiedState is UiState.Loading) {
        LoadingScreen()
    }

    LaunchedEffect(isEmailVerifiedState) {
        when {
            isEmailVerifiedState is UiState.Success -> {
                navigateToPasswordCheck()
                viewModel.deleteTempAccount()
            }

            isEmailVerifiedState is UiState.Error -> {
                val errorMessage =
                    (isEmailVerifiedState as UiState.Error).errorMessage ?: "알 수 없는 오류가 발생했습니다."
                onShowSnackBar(errorMessage)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetEmailVerifiedState()
            viewModel.deleteTempAccount()
        }
    }
}

@Composable
fun EmailVerificationScreen(
    padding: PaddingValues,
    popBackStack: () -> Unit = {},
    onCheckEmailVerified: () -> Unit = {}
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
            modifier = Modifier.fillMaxSize()
        ) {
            BackArrow(
                onBackClick = popBackStack,
                modifier = Modifier.align(Alignment.TopStart)
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
                    text = "이메일 인증",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(
                    text = "귀하의 이메일로 인증 링크를 발송했습니다." +
                            " 발송한 메일의 링크를 클릭하여 본인 임을 입증해 주세요!",
                    color = Color.Black
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                    onClick = { onCheckEmailVerified() }
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