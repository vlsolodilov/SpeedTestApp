package com.solodilov.presentation.screen.speedtest.business

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solodilov.domain.usecase.CheckInternetConnectionUseCase
import com.solodilov.domain.usecase.GetCurrentDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetCurrentUploadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedDownloadSpeedUseCase
import com.solodilov.domain.usecase.GetFinishedUploadSpeedUseCase
import com.solodilov.domain.usecase.GetSettingsUseCase
import com.solodilov.domain.usecase.GetTestStatusUseCase
import com.solodilov.domain.usecase.StartTestUseCase
import com.solodilov.util.BasicSideEffect
import com.solodilov.util.annotation.TestStatus
import kotlinx.coroutines.flow.combine
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container

class TestViewModel(
    savedStateHandle: SavedStateHandle,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase,
    private val startTestUseCase: StartTestUseCase,
    private val getTestStatusUseCase: GetTestStatusUseCase,
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
                getTestStatusUseCase(),
                getCurrentDownloadSpeedUseCase(),
                getCurrentUploadSpeedUseCase(),
            ) { isConnected, testStatus, downloadSpeed, uploadSpeed ->
                state.copy(
                    isConnected = isConnected,
                    testStatus = testStatus,
                    downloadSpeed = if (testStatus == TestStatus.FINISHED) getFinishedDownloadSpeedUseCase() else downloadSpeed,
                    uploadSpeed = if (testStatus == TestStatus.FINISHED) getFinishedUploadSpeedUseCase() else uploadSpeed,
                )
            }.collect { newState ->
                reduce { newState }
            }
        }
    }

    fun onStartClick() = intent {
        if (state.isConnected) {
            reduce { state.copy(showLocationPermissionDialog = true) }
        } else {
            return@intent
        }
    }

    fun onLocationPermissionGranted() = intent {
        reduce { state.copy(showLocationPermissionDialog = false) }
        startTestUseCase()
    }

    fun hidePermissionDialog() = intent {
        reduce { state.copy(showLocationPermissionDialog = false) }
    }

}