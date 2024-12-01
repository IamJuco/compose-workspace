package com.workspace.feature.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.workspace.feature.home.HomeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color

@Composable
fun PokemonCard(viewModel: HomeViewModel) {
    val pokemonList by viewModel.pokemonList.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == pokemonList.size - 1) {
                    viewModel.fetchPokemonList()
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = gridState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemonList) { pokemon ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = pokemon.imageUrl,
                                contentDescription = "Pokemon Image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "이름: ${pokemon.name}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}



@Preview
@Composable
fun PreviewPokemonCard() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    PokemonCard(viewModel = homeViewModel)
}