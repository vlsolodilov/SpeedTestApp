package com.solodilov.data.repository

import android.content.SharedPreferences
import com.solodilov.domain.entity.MainSettings
import com.solodilov.domain.repository.SettingsManager
import com.solodilov.util.annotation.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsManagerImpl(
    private val pref: SharedPreferences,
) : SettingsManager {

    private val _themeMode = MutableStateFlow(pref.getInt(THEME_MODE, ThemeMode.SYSTEM))

    override fun getThemeMode(): Flow<Int> = _themeMode

    override fun getSettings(): MainSettings {
        return MainSettings(
            themeMode = pref.getInt(THEME_MODE, ThemeMode.SYSTEM),
            isCheckDownloadSpeed = pref.getBoolean(CHECK_DOWNLOAD, true),
            downloadUrl = pref.getString(DOWNLOAD_URL, "") ?: "",
            isCheckUploadSpeed = pref.getBoolean(CHECK_UPLOAD, true),
            uploadUrl = pref.getString(UPLOAD_URL, "") ?: "",
        )
    }

    override fun saveSettings(settings: MainSettings) {
        with(pref.edit()) {
            putInt(THEME_MODE, settings.themeMode)
            putBoolean(CHECK_DOWNLOAD, settings.isCheckDownloadSpeed)
            putString(DOWNLOAD_URL, settings.downloadUrl)
            putBoolean(CHECK_UPLOAD, settings.isCheckUploadSpeed)
            putString(UPLOAD_URL, settings.uploadUrl)
            apply()
        }
        _themeMode.value = settings.themeMode
    }

    private companion object {
        const val THEME_MODE = "themeMode"
        const val CHECK_DOWNLOAD = "checkDownload"
        const val CHECK_UPLOAD = "checkUpload"
        const val DOWNLOAD_URL = "downloadUrl"
        const val UPLOAD_URL = "uploadUrl"
    }
}