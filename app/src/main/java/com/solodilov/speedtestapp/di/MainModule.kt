package com.solodilov.speedtestapp.di

import com.solodilov.speedtestapp.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(
            checkThemeUseCase = get(),
        )
    }
}