package com.workspace.feature.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.workspace.core.domain.api_error_handle.ApiException
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.model.UiState
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success -> {
            val pagingDataFlow = (uiState as UiState.Success<Flow<PagingData<PokemonList>>>).data
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()

            LazyColumn {
                items(lazyPagingItems.itemCount) { index ->
                    val pokemon = lazyPagingItems[index]
                    pokemon?.let {
                        PokemonItem(it)
                    }
                }
            }
        }

        is UiState.Failure -> {
            val exception = (uiState as UiState.Failure).exception
            ErrorDialog(exception)
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonList) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = "Pokemon Image",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = pokemon.name, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ErrorDialog(exception: ApiException) {
    val errorMessage = when (exception) {
        is ApiException.BadRequest -> "잘못된 요청입니다."
        is ApiException.Unauthorized -> "인증되지 않았습니다."
        is ApiException.Forbidden -> "접근이 금지되었습니다."
        is ApiException.NotFound -> "리소스를 찾을 수 없습니다."
        is ApiException.TimeOut -> "요청 시간이 초과되었습니다."
        is ApiException.ServerError -> "서버 오류가 발생했습니다."
        is ApiException.InternetError -> "인터넷 연결이 불안정합니다."
        is ApiException.Unknown -> "알 수 없는 오류가 발생했습니다."
    }

    AlertDialog(
        onDismissRequest = { /* 다이얼로그 닫기 */ },
        title = { Text("에러 발생") },
        text = { Text(errorMessage) },
        confirmButton = {
            Button(onClick = { /* 확인 */ }) {
                Text("확인")
            }
        }
    )
}