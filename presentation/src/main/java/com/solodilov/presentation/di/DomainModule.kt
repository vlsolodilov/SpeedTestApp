package com.solodilov.presentation.di

import com.solodilov.presentation.screen.settings.business.SettingsViewModel
import com.solodilov.presentation.screen.speedtest.business.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        TestViewModel(
            savedStateHandle = get(),
            checkInternetConnectionUseCase = get(),
            startTestUseCase = get(),
            getTestStatusUseCase = get(),
            getCurrentDownloadSpeedUseCase = get(),
            getFinishedDownloadSpeedUseCase = get(),
            getCurrentUploadSpeedUseCase = get(),
            getFinishedUploadSpeedUseCase = get(),
            getSettingsUseCase = get(),
        )
    }
    viewModel {
        SettingsViewModel(
            savedStateHandle = get(),
            getSettingsUseCase = get(),
            saveSettingsUseCase = get(),
            provider = get(),
        )
    }
}