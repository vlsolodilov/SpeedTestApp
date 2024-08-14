package com.solodilov.domain.di

import com.solodilov.domain.usecase.CheckInternetConnectionUseCase
import com.solodilov.domain.usecase.CheckThemeUseCase
import com.solodilov.domain.usecase.GetCurrentDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetCurrentUploadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedUploadSpeedUseCase
import com.solodilov.domain.usecase.GetSettingsUseCase
import com.solodilov.domain.usecase.GetDownloadTestStatusUseCase
import com.solodilov.domain.usecase.GetUploadTestStatusUseCase
import com.solodilov.domain.usecase.SaveSettingsUseCase
import com.solodilov.domain.usecase.StartDownloadTestUseCase
import com.solodilov.domain.usecase.StartUploadTestUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CheckInternetConnectionUseCase(repository = get()) }
    factory { CheckThemeUseCase(repository = get()) }
    factory { GetSettingsUseCase(repository = get()) }
    factory { SaveSettingsUseCase(repository = get()) }
    factory { GetDownloadTestStatusUseCase(repository = get()) }
    factory { GetUploadTestStatusUseCase(repository = get()) }
    factory { StartDownloadTestUseCase(repository = get()) }
    factory { StartUploadTestUseCase(repository = get()) }
    factory { GetCurrentDownloadSpeedUseCase(repository = get()) }
    factory { GetFinishedDownloadSpeedUseCase(repository = get()) }
    factory { GetCurrentUploadSpeedUseCase(repository = get()) }
    factory { GetFinishedUploadSpeedUseCase(repository = get()) }
}