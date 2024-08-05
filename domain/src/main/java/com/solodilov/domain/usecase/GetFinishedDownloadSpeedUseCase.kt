package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SpeedTestManager

class GetFinishedDownloadSpeedUseCase(private val repository: SpeedTestManager) {

    operator fun invoke(): Double = repository.getDownloadSpeed()
}