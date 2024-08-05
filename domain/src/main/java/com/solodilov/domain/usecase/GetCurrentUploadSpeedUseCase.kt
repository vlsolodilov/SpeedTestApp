package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SpeedTestManager
import kotlinx.coroutines.flow.Flow

class GetCurrentUploadSpeedUseCase(private val repository: SpeedTestManager) {

    operator fun invoke(): Flow<Double> = repository.getCurrentUploadSpeed()
}