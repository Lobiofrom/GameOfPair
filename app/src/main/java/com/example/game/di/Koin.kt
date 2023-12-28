package com.example.game.di

import com.example.data.repoImpl.RepoImpl
import com.example.domain.repo.Repo
import com.example.domain.useCases.CreateCardUseCase
import com.example.game.viewmodel.MyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repo> { RepoImpl() }

    factory { CreateCardUseCase(get()) }

    viewModel { MyViewModel(get()) }

}
