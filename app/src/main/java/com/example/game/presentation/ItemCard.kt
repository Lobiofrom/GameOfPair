package com.example.game.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.Card
import com.example.game.ui.theme.GameTheme

@Composable
fun ItemCard(
    card: Card,
    onClick: () -> Unit,
    start: Boolean
) {

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = card.alpha == 0f && start,
                onClick = onClick
            )
            .padding(6.dp),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 10.dp,
            disabledElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Yellow
        )
    ) {
        Image(
            painter = painterResource(id = card.image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alpha = card.alpha
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    GameTheme {
        //ItemCard()
    }
}