package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SpeedTestManager
import kotlinx.coroutines.flow.Flow

class GetTestStatusUseCase(private val repository: SpeedTestManager) {

    operator fun invoke(): Flow<Int> = repository.getStatus()
}