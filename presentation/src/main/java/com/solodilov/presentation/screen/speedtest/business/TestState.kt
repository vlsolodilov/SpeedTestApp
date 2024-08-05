package com.solodilov.presentation.screen.speedtest.business

import android.os.Parcelable
import com.solodilov.util.annotation.TestStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestState(
    val isConnected: Boolean = false,
    @TestStatus val testStatus: Int = TestStatus.NO_STARTED,
    val downloadSpeed: Double = 0.0,
    val uploadSpeed: Double = 0.0,
    val isVisibleDownload: Boolean = false,
    val isVisibleUpload: Boolean = false,
    val showLocationPermissionDialog: Boolean = false,
): Parcelable
