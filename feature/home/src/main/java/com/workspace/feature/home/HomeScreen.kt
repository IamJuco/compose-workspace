package com.workspace.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.PokemonList
import com.workspace.feature.home.component.PokemonCard

const val GRID_COLUMN = 2

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val pokemonList = homeViewModel.pokemonPagingData.collectAsLazyPagingItems()

    when (pokemonList.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingScreen()
        }

        is LoadState.Error -> {
            val error = (pokemonList.loadState.refresh as LoadState.Error).error
            ErrorScreen(errorMessage = error.message ?: ErrorCode.UNKNOWN_ERROR.message) {
                pokemonList.retry()
            }
        }

        else -> {
            HomeScreen(pokemonList = pokemonList)
        }
    }
}

@Composable
fun HomeScreen(
    pokemonList: LazyPagingItems<PokemonList>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_COLUMN),
        ) {
            items(pokemonList.itemCount) { index ->
                val pokemon = pokemonList[index]
                if (pokemon != null) {
                    PokemonCard(pokemon)
                }
            }
        }
        pokemonList.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    LoadingScreen()
                }

                loadState.append is LoadState.Error -> {
                    val error = (loadState.append as LoadState.Error).error
                    ErrorScreen(
                        errorMessage = error.message ?: ErrorCode.UNKNOWN_ERROR.message
                    ) {
                        retry()
                    }
                }
            }
        }
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