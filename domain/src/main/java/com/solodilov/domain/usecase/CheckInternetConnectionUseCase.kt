package com.solodilov.domain.usecase

import com.solodilov.domain.repository.InternetCheck
import kotlinx.coroutines.flow.Flow

class CheckInternetConnectionUseCase(private val repository: InternetCheck) {

    suspend operator fun invoke(): Flow<Boolean> = repository.isInternetEnabled()
}