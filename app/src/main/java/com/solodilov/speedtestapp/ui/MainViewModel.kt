package com.solodilov.speedtestapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodilov.domain.usecase.CheckThemeUseCase
import com.solodilov.util.annotation.ThemeMode
import kotlinx.coroutines.launch

class MainViewModel(
    private val checkThemeUseCase: CheckThemeUseCase,
) : ViewModel() {

    private val _themeMode = mutableIntStateOf(ThemeMode.SYSTEM)
    val themeMode: State<Int> = _themeMode

    init {
        viewModelScope.launch {
            checkThemeUseCase().collect { theme ->
                _themeMode.intValue = theme
            }
        }
    }
}