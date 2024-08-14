package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SpeedTestManager

class StartDownloadTestUseCase(private val repository: SpeedTestManager) {

    operator fun invoke() {
        repository.startDownloadTest()
    }
}