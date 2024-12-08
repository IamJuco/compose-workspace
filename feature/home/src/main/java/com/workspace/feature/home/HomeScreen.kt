package com.workspace.feature.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.PokemonList
import com.workspace.feature.home.component.PokemonCard
import com.workspace.feature.home.component.SearchCard

const val GRID_COLUMN = 2

@Composable
fun HomeRoute(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val pokemonList = homeViewModel.pokemonPagingData.collectAsLazyPagingItems()
    val searchQuery by homeViewModel.searchQuery.collectAsStateWithLifecycle()

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
            HomeScreen(
                pokemonList = pokemonList,
                searchQuery = searchQuery,
                onSearchQueryChange = { homeViewModel.updateSearchQuery(it) },
                onSearch = { homeViewModel.applySearchQuery() },
                onPokemonClick = { pokemonId ->
                    navController.navigate("detail/$pokemonId")
                }
            )
        }
    }
}

@Composable
fun HomeScreen(
    pokemonList: LazyPagingItems<PokemonList>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onPokemonClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        SearchCard(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch = onSearch
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(GRID_COLUMN),
            ) {
                items(pokemonList.itemCount) { index ->
                    val pokemon = pokemonList[index]
                    if (pokemon != null) {
                        PokemonCard(
                            pokemon = pokemon,
                            onClick = { onPokemonClick(pokemon.id) }
                        )
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