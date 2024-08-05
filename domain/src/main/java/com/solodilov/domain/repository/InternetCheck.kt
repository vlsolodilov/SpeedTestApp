package com.solodilov.domain.repository

import kotlinx.coroutines.flow.Flow

interface InternetCheck {
    suspend fun isInternetEnabled(): Flow<Boolean>
}