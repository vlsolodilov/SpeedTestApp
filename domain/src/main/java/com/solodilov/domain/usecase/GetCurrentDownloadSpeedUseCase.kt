package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SpeedTestManager
import kotlinx.coroutines.flow.Flow

class GetCurrentDownloadSpeedUseCase(private val repository: SpeedTestManager) {

    operator fun invoke(): Flow<Double> = repository.getCurrentDownloadSpeed()
}