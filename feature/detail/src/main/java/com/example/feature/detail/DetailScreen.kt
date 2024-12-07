package com.example.feature.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.ServiceResult

@Composable
fun DetailRoute(
    pokemonId: Int,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val pokemonDetail = detailViewModel.pokemonDetail.collectAsStateWithLifecycle()

    LaunchedEffect(pokemonId) {
        detailViewModel.loadPokemonDetail(pokemonId)
    }

    when (val result = pokemonDetail.value) {
        is ServiceResult.Loading -> LoadingScreen()
        is ServiceResult.Success -> DetailScreen(pokemon = result.data)
        is ServiceResult.Error -> {
            ErrorScreen(
                errorMessage = result.error.message,
                onRetry = { detailViewModel.loadPokemonDetail(pokemonId) }
            )
        }
    }
}

@Composable
fun DetailScreen(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = "${pokemon.name} Image",
            modifier = Modifier
                .size(300.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = "Height: ${pokemon.height}")
        Text(text = "Weight: ${pokemon.weight}")
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