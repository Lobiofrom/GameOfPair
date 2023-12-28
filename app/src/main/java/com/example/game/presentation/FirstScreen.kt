package com.example.game.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.R
import com.example.game.viewmodel.MyViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FirstScreen(
    onClick: () -> Unit,
    viewModel: MyViewModel = koinViewModel()
) {
    viewModel.start()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_brightness_1_24),
                contentDescription = null
            )
            Text(
                text = "100", fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.touch),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
        )
        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        ) {
            Text(text = "PLAY")
        }
    }
}