package com.solodilov.domain.usecase

import com.solodilov.domain.entity.MainSettings
import com.solodilov.domain.repository.SettingsManager

class GetSettingsUseCase(private val repository: SettingsManager) {

    operator fun invoke(): MainSettings = repository.getSettings()
}