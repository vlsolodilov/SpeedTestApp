package com.solodilov.domain.di

import com.solodilov.domain.usecase.CheckInternetConnectionUseCase
import com.solodilov.domain.usecase.CheckThemeUseCase
import com.solodilov.domain.usecase.GetCurrentDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetCurrentUploadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedUploadSpeedUseCase
import com.solodilov.domain.usecase.GetSettingsUseCase
import com.solodilov.domain.usecase.GetTestStatusUseCase
import com.solodilov.domain.usecase.SaveSettingsUseCase
import com.solodilov.domain.usecase.StartTestUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CheckInternetConnectionUseCase(repository = get()) }
    factory { CheckThemeUseCase(repository = get()) }
    factory { GetSettingsUseCase(repository = get()) }
    factory { SaveSettingsUseCase(repository = get()) }
    factory { GetTestStatusUseCase(repository = get()) }
    factory { StartTestUseCase(repository = get()) }
    factory { GetCurrentDownloadSpeedUseCase(repository = get()) }
    factory { GetFinishedDownloadSpeedUseCase(repository = get()) }
    factory { GetCurrentUploadSpeedUseCase(repository = get()) }
    factory { GetFinishedUploadSpeedUseCase(repository = get()) }
}