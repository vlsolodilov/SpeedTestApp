package com.solodilov.domain.repository

import kotlinx.coroutines.flow.Flow

interface SpeedTestManager {
    fun startTest()
    fun getStatus(): Flow<Int>
    fun getDownloadSpeed(): Double
    fun getCurrentDownloadSpeed(): Flow<Double>
    fun getUploadSpeed(): Double
    fun getCurrentUploadSpeed(): Flow<Double>
}