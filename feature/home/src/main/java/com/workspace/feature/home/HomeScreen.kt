package com.workspace.feature.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.workspace.core.domain.api_error_handle.ApiException
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.model.UiState
import com.workspace.feature.home.component.PokemonCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {
            Log.d("HomeScreen", "Loading 상태")
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        }

        is UiState.Success -> {
            Log.d("HomeScreen", "Success 상태")
            val pagingDataFlow = (uiState as UiState.Success<Flow<PagingData<PokemonList>>>).data
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
            HomeScreen(
                pokemonItems = lazyPagingItems
            )
        }

        is UiState.Failure -> {
            Log.d("HomeScreen", "Failure 상태")
            val exception = (uiState as UiState.Failure).exception
            ErrorScreen(exception)
        }
    }
}

@Composable
fun HomeScreen(
    pokemonItems: LazyPagingItems<PokemonList>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokemonItems.itemCount) { index ->
            val pokemon = pokemonItems[index]
            pokemon?.let {
                PokemonCard(it)
            }
        }
    }
}

@Composable
fun ErrorScreen(exception: ApiException) {
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
}