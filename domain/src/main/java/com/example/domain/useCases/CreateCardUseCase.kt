package com.example.domain.useCases

import com.example.domain.models.Card
import com.example.domain.repo.Repo

class CreateCardUseCase(
    private val repo: Repo
) {
    suspend fun execute(id: Long): Card {
        return repo.createCard(id)
    }
}