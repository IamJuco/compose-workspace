package com.workspace.feature.mypage

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.workspace.core.domain.model.UiState

@Composable
fun MyPageRoute(
    padding: PaddingValues = PaddingValues(),
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by myPageViewModel.uiState.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                myPageViewModel.updateProfileImage(uri.toString())
            }
        }
    )

    LaunchedEffect(Unit) {
        if (uiState is UiState.Idle) {
            myPageViewModel.loadUserProfile()
        }
    }

    if (uiState is UiState.Loading) {
        LoadingScreen()
    }

    val profileImageUrl = (uiState as? UiState.Success)?.data

    MyPageScreen(
        padding = padding,
        profileImageUrl = profileImageUrl,
        onImageClick = { launcher.launch("image/*") },
        onLogoutClick = { myPageViewModel.logout() }
    )
}


@Composable
fun MyPageScreen(
    padding: PaddingValues = PaddingValues(),
    profileImageUrl: String?,
    onImageClick: () -> Unit,
    onLogoutClick:() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = profileImageUrl
                ?: "https://media.licdn.com/dms/image/v2/D5612AQGjfqyhNzC_nw/article-cover_image-shrink_720_1280/article-cover_image-shrink_720_1280/0/1701381590197?e=2147483647&v=beta&t=7YquuYqT9yTy5m9a2CG6R3QCXCxfShxWiaQbUeRZCbo",
            contentDescription = "내 프로필",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color.Gray, CircleShape)
                .clickable { onImageClick() }
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "닉네임을 변경해 보세요!",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .border(width = 1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
            text = "프로필 수정"
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .border(width = 1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
                .clickable { onLogoutClick() },
            text = "로그아웃"
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

//@Composable
//@Preview(showBackground = true)
//fun PreviewMyPageScreen() {
//    MyPageScreen()
//}