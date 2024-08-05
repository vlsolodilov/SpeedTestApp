package com.solodilov.domain.usecase

import android.util.Log
import com.solodilov.domain.repository.SpeedTestManager

class StartTestUseCase(private val repository: SpeedTestManager) {

    operator fun invoke() {
        Log.e("TAG", "invoke: ", )
        repository.startTest()
    }
}