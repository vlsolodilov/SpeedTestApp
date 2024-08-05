package com.solodilov.presentation.screen.settings.business

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solodilov.domain.entity.MainSettings
import com.solodilov.domain.usecase.GetSettingsUseCase
import com.solodilov.domain.usecase.SaveSettingsUseCase
import com.solodilov.util.BasicSideEffect
import com.solodilov.util.R
import com.solodilov.util.ShowMessage
import com.solodilov.util.StringResourceProvider
import com.solodilov.util.annotation.ThemeMode
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.repeatOnSubscription
import org.orbitmvi.orbit.viewmodel.container

class SettingsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val provider: StringResourceProvider,
) : ViewModel(), ContainerHost<SettingsState, BasicSideEffect> {

    override val container = container<SettingsState, BasicSideEffect>(
        initialState = SettingsState(),
        savedStateHandle = savedStateHandle
    ) {
        initializeState()
    }

    private fun initializeState() = intent {
        repeatOnSubscription {
            getSettings()
        }
    }

    private fun getSettings() = intent {
        val settings = getSettingsUseCase()
        reduce {
            state.copy(
                selectedTheme = settings.themeMode,
                isCheckedDownload = settings.isCheckDownloadSpeed,
                downloadUrl = settings.downloadUrl,
                isCheckedUpload = settings.isCheckUploadSpeed,
                uploadUrl = settings.uploadUrl,
            )
        }
    }

    fun onThemeChange(@ThemeMode newTheme: Int) = intent {
        reduce { state.copy(selectedTheme = newTheme) }
    }

    fun onDownloadClick(newValue: Boolean) = intent {
        reduce { state.copy(isCheckedDownload = newValue) }
    }

    fun onDownloadUrlChange(url: String) = intent {
        reduce { state.copy(downloadUrl = url) }
    }

    fun onUploadClick(newValue: Boolean) = intent {
        reduce { state.copy(isCheckedUpload = newValue) }
    }

    fun onUploadUrlChange(url: String) = intent {
        reduce { state.copy(uploadUrl = url) }
    }


    fun onCancelClick() = intent {
        getSettings()
    }

    fun onSaveSettingsClick() = intent {
        if (!state.isCheckedDownload && !state.isCheckedUpload) {
            postSideEffect(ShowMessage(provider.getString(R.string.error_massage)))
            return@intent
        }
        saveSettingsUseCase(
            MainSettings(
                themeMode = state.selectedTheme,
                isCheckDownloadSpeed = state.isCheckedDownload,
                downloadUrl = state.downloadUrl,
                isCheckUploadSpeed = state.isCheckedUpload,
                uploadUrl = state.uploadUrl,
            )
        )
    }
}