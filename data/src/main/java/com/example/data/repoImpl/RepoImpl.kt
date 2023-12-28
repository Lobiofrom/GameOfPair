package com.example.data.repoImpl

import com.example.data.R
import com.example.domain.models.Card
import com.example.domain.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoImpl : Repo {
    override suspend fun createCard(id: Long): Card {
        return withContext(Dispatchers.IO) {
            val image = listOf(
                R.drawable.one,
                R.drawable.two,
                R.drawable.three,
                R.drawable.four,
                R.drawable.five,
                R.drawable.six,
                R.drawable.seven,
                R.drawable.eight,
                R.drawable.nine,
                R.drawable.ten,
            ).random()
            Card(
                image = image,
                alpha = 0f,
                id = id
            )
        }
    }
}