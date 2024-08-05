package com.solodilov.util

sealed interface BasicSideEffect

data class NavigateTo(val route: String) : BasicSideEffect
data class ShowMessage(val message: String) : BasicSideEffect