package com.example.game.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.models.Card
import com.example.domain.models.CardList
import com.example.domain.useCases.CreateCardUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel(
    private val createCardUseCase: CreateCardUseCase
) : ViewModel() {
    private var _list = MutableStateFlow(CardList())
    val list = _list.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun start() {
        val list = mutableListOf<Card>()
        var id = 1000L
        scope.launch {
            (0 until 20).map {
                async {
                    list.add(createCardUseCase.execute(id = id++))
                }
            }.awaitAll()
            _list.value = _list.value.copy(cardList = list)
        }
    }

    fun updateCardAlpha(card: Card, alpha: Float) {
        scope.launch {
            val newList = _list.value.cardList.toMutableList()
            val newCard = newList.indexOf(card)
            newList[newCard] = newList[newCard].copy(
                alpha = alpha,
                id = card.id,
                image = card.image
            )
            _list.value = _list.value.copy(cardList = newList)
        }
    }

    fun deleteCard(cardId1: Long, cardId2: Long) {
        scope.launch {
            val newList = _list.value.cardList.toMutableList()
            val index1 = newList.indexOfFirst { it.id == cardId1 }
            var index2 = newList.indexOfFirst { it.id == cardId2 }

            newList.removeAt(index1)
            if (index2 > index1) {
                index2 -= 1
            }
            newList.removeAt(index2)
            _list.value = _list.value.copy(cardList = newList)
        }
    }
}