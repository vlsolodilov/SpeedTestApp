package com.solodilov.presentation.screen.settings.business

import android.os.Parcelable
import com.solodilov.util.annotation.ThemeMode
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsState(
    @ThemeMode val selectedTheme: Int = ThemeMode.SYSTEM,
    val isCheckedDownload: Boolean = false,
    val downloadUrl: String = "",
    val isCheckedUpload: Boolean = false,
    val uploadUrl: String = "",
): Parcelable
