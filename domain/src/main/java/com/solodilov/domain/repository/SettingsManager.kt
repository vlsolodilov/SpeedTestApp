package com.solodilov.domain.repository

import com.solodilov.domain.entity.MainSettings
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    fun getThemeMode(): Flow<Int>
    fun getSettings(): MainSettings
    fun saveSettings(settings: MainSettings)
}