package com.solodilov.presentation.screen.speedtest.business

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solodilov.domain.usecase.CheckInternetConnectionUseCase
import com.solodilov.domain.usecase.GetCurrentDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetCurrentUploadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedUploadSpeedUseCase
import com.solodilov.domain.usecase.GetSettingsUseCase
import com.solodilov.domain.usecase.GetDownloadTestStatusUseCase
import com.solodilov.domain.usecase.GetUploadTestStatusUseCase
import com.solodilov.domain.usecase.StartDownloadTestUseCase
import com.solodilov.domain.usecase.StartUploadTestUseCase
import com.solodilov.util.BasicSideEffect
import com.solodilov.util.annotation.TestStatus
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container

class TestViewModel(
    savedStateHandle: SavedStateHandle,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase,
    private val startDownloadTestUseCase: StartDownloadTestUseCase,
    private val startUploadTestUseCase: StartUploadTestUseCase,
    private val getDownloadTestStatusUseCase: GetDownloadTestStatusUseCase,
    private val getUploadTestStatusUseCase: GetUploadTestStatusUseCase,
    private val getCurrentDownloadSpeedUseCase: GetCurrentDownloadSpeedUseCase,
    private val getFinishedDownloadSpeedUseCase: GetFinishedDownloadSpeedUseCase,
    private val getCurrentUploadSpeedUseCase: GetCurrentUploadSpeedUseCase,
    private val getFinishedUploadSpeedUseCase: GetFinishedUploadSpeedUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
) : ViewModel(), ContainerHost<TestState, BasicSideEffect> {

    override val container = container<TestState, BasicSideEffect>(
        initialState = TestState(),
        savedStateHandle = savedStateHandle
    ) {
        initializeState()
    }

    private fun initializeState() = intent {
        repeatOnSubscription {
            reduce {
                val settings = getSettingsUseCase()
                state.copy(
                    isVisibleDownload = settings.isCheckDownloadSpeed,
                    isVisibleUpload = settings.isCheckUploadSpeed,
                )
            }
            combine(
                checkInternetConnectionUseCase(),
                getDownloadTestStatusUseCase(),
                getUploadTestStatusUseCase(),
                getCurrentDownloadSpeedUseCase(),
                getCurrentUploadSpeedUseCase(),
            ) { isConnected, testDownloadStatus, testUploadStatus, downloadSpeed, uploadSpeed ->
                state.copy(
                    isConnected = isConnected,
                    testDownloadStatus = testDownloadStatus,
                    testUploadStatus = testUploadStatus,
                    downloadSpeed = if (testDownloadStatus == TestStatus.FINISHED) getFinishedDownloadSpeedUseCase() else downloadSpeed,
                    uploadSpeed = if (testUploadStatus == TestStatus.FINISHED) getFinishedUploadSpeedUseCase() else uploadSpeed,
                )
            }.collect { newState ->
                reduce { newState }
            }
        }
    }

    fun onStartClick() = intent {
        reduce { state.copy(
            downloadSpeed = 0.0,
            uploadSpeed = 0.0,
        ) }
        if (state.isConnected) {
            if (state.isVisibleDownload && state.isVisibleUpload) {
                startDownloadTestUseCase()
                getDownloadTestStatusUseCase()
                    .filter { it == TestStatus.FINISHED }
                    .first()
                    .also { startUploadTestUseCase() }
            } else if (state.isVisibleDownload) {
                startDownloadTestUseCase()
            } else if (state.isVisibleUpload) {
                startUploadTestUseCase()
            }
        } else {
            return@intent
        }
    }

    fun onLocationPermissionGranted() = intent {
        reduce { state.copy(showLocationPermissionDialog = false) }
        startUploadTestUseCase()
    }

    fun hidePermissionDialog() = intent {
        reduce { state.copy(showLocationPermissionDialog = false) }
    }

}