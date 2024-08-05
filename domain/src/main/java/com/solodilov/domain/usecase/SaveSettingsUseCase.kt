package com.solodilov.domain.usecase

import com.solodilov.domain.entity.MainSettings
import com.solodilov.domain.repository.SettingsManager

class SaveSettingsUseCase(private val repository: SettingsManager) {

    operator fun invoke(settings: MainSettings) {
        repository.saveSettings(settings)
    }
}