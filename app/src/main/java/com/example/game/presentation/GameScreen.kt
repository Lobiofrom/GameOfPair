package com.example.game.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.Card
import com.example.game.R
import com.example.game.viewmodel.MyViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    viewModel: MyViewModel = koinViewModel()
) {
    var list by remember {
        mutableStateOf<List<Card>>(emptyList())
    }
    var openedCards by remember {
        mutableStateOf<List<Card>>(emptyList())
    }
    val scope = rememberCoroutineScope()

    var time by remember {
        mutableIntStateOf(0)
    }

    var job by remember {
        mutableStateOf<Job?>(null)
    }

    var startGame by remember {
        mutableStateOf(false)
    }
    var showGame by remember {
        mutableStateOf(false)
    }
    var showFirst by remember {
        mutableStateOf(true)
    }
    var showLast by remember {
        mutableStateOf(false)
    }

    var buttonEnabled by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(list) {
        showLast = false
        if (checkOpenedCard(list)) {
            job?.cancel()
            openedCards = emptyList()
            delay(300)
            showGame = false
            showLast = true
        }
        viewModel.list.collect {
            list = it.cardList
        }
    }
    if (showFirst) {
        FirstScreen(onClick = {
            showGame = true
            showFirst = false
            showLast = true
        })
    }
    if (showGame) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .padding(start = 30.dp, top = 15.dp),
                    shape = RoundedCornerShape(35.dp),
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
                            .padding(15.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_access_alarm_24),
                            contentDescription = null,
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                        )
                        Text(
                            text = formatTime(time), modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp)
                                .align(Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 70.dp, end = 16.dp)
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
            }
            Text(
                text = "Less time, More Reward!",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 38.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.padding(6.dp)
            ) {
                items(
                    list
                ) { card ->
                    ItemCard(
                        card = card,
                        start = startGame,
                        onClick = {
                            scope.launch {
                                delay(150)
                                viewModel.updateCardAlpha(card, alpha = 1f)

                                if (openedCards.isNotEmpty()) {
                                    val matchingCard = openedCards.find { it.image == card.image }
                                    if (matchingCard != null) {
                                        openedCards = openedCards.toMutableList().apply {
                                            remove(matchingCard)
                                        }
                                        delay(190)
                                        viewModel.deleteCard(matchingCard.id, card.id)

                                    } else {
                                        openedCards = openedCards + card
                                    }
                                } else {
                                    openedCards = listOf(card)
                                }
                            }
                        })
                }
            }
            Text(
                text = "Keep matching up two of the same object until there are no more to be paired and you clear the board",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(6.dp)
            )
            Button(
                onClick = {
                    startGame = true
                    buttonEnabled = false
                    job = scope.launch {
                        while (time >= 0) {
                            delay(1000)
                            time++
                        }
                    }
                },
                enabled = buttonEnabled,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .width(200.dp)
            ) {
                Text(text = "Start")
            }
        }
    }
    if (checkOpenedCard(list) && showLast) {
        LastScreen(coins = calculateReward(time).toString(), onClick = {
            showFirst = true
            showLast = false
            time = 0
            startGame = false
            buttonEnabled = true
        })
    }
}

fun calculateReward(time: Int): Int {
    val maximumReward = 100
    val minimumReward = 10
    val timeThreshold = 20
    val penaltyPerSecond = 5

    return when {
        time < timeThreshold -> maximumReward
        else -> {
            val penalty = (time - timeThreshold) * penaltyPerSecond
            val reward = maximumReward - penalty
            maxOf(reward, minimumReward)
        }
    }
}

fun checkOpenedCard(list: List<Card>): Boolean {
    return list.all {
        it.alpha == 1f
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d : %02d", minutes, remainingSeconds)
}
