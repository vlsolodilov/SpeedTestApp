package com.solodilov.domain.usecase

import com.solodilov.domain.repository.SpeedTestManager
import kotlinx.coroutines.flow.Flow

class GetUploadTestStatusUseCase(private val repository: SpeedTestManager) {

    operator fun invoke(): Flow<Int> = repository.getUploadStatus()
}