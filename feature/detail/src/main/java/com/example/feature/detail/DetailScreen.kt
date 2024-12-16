package com.example.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.detail.component.PokemonImageCard
import com.example.feature.detail.component.PokemonStatusCard
import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.UiState

@Composable
fun DetailRoute(
    pokemonId: Int,
    detailViewModel: DetailViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    padding: PaddingValues = PaddingValues()
) {
    val state by detailViewModel.pokemonDetailState.collectAsStateWithLifecycle()

    LaunchedEffect(pokemonId) {
        detailViewModel.loadPokemonDetail(pokemonId)
    }

    when (state) {
        is UiState.Idle -> LoadingScreen()
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            val pokemon = (state as UiState.Success<Pokemon>).data
            DetailScreen(
                pokemon = pokemon,
                onBackClick = { popBackStack() },
                padding = padding
            )
        }

        is UiState.Error -> {
            val errorMessage = (state as UiState.Error).errorMessage ?: "Unknown Error"
            ErrorScreen(
                errorMessage = errorMessage,
                onRetry = { detailViewModel.loadPokemonDetail(pokemonId) }
            )
        }
    }
}

@Composable
fun DetailScreen(
    pokemon: Pokemon,
    onBackClick: () -> Unit,
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        PokemonImageCard(imageUrl = pokemon.imageUrl, name = pokemon.name)

        BackArrow(
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = pokemon.name,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                PokemonStatusCard(label = "키", value = pokemon.height.toFloat(), unit = "M")
                Spacer(modifier = Modifier.width(8.dp))
                PokemonStatusCard(label = "몸무게", value = pokemon.weight.toFloat(), unit = "KG")
            }
        }
    }
}

@Composable
fun BackArrow(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
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