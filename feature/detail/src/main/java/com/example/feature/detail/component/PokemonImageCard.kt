package com.example.feature.detail.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PokemonImageCard(imageUrl: String, name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(bottomEnd = 64.dp, bottomStart = 64.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$name 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.3f),
            contentScale = ContentScale.Fit
        )
    }
}