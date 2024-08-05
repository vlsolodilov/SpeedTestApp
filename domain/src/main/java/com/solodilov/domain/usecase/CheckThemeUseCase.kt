package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SettingsManager
import kotlinx.coroutines.flow.Flow

class CheckThemeUseCase(private val repository: SettingsManager) {

    operator fun invoke(): Flow<Int> = repository.getThemeMode()
}