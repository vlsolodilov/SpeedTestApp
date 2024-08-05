package com.solodilov.domain.entity

import com.solodilov.util.annotation.ThemeMode

data class MainSettings(
    @ThemeMode val themeMode: Int,
    val isCheckDownloadSpeed: Boolean,
    val downloadUrl: String,
    val isCheckUploadSpeed: Boolean,
    val uploadUrl: String,
)
