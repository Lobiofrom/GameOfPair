package com.example.domain.repo

import com.example.domain.models.Card

interface Repo {
    suspend fun createCard(id: Long): Card
}