package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SpeedTestManager

class StartUploadTestUseCase(private val repository: SpeedTestManager) {

    operator fun invoke() {
        repository.startUploadTest()
    }
}