package com.example.game.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.R
import com.example.game.ui.theme.GameTheme

@Composable
fun LastScreen(
    coins: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.trophy),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 96.dp)
        )
        Text(
            text = "[CONGRATULATIONS!]", fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "[Great! You won!]", fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Card(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
                pressedElevation = 10.dp,
                disabledElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_brightness_1_24),
                    contentDescription = null
                )
                Text(
                    text = coins, fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(30.dp)
        ) {
            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Double Reward")
            }
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onClick()
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prevy() {
    GameTheme {
        LastScreen("50", onClick = {})
    }
}