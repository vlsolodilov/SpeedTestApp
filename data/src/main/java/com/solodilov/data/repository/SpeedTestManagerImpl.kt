package com.solodilov.data.repository

import android.util.Log
import com.solodilov.domain.repository.SpeedTestManager
import com.solodilov.util.annotation.TestStatus
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.inter.ISpeedTestListener
import fr.bmartel.speedtest.model.SpeedTestError
import fr.bmartel.speedtest.utils.SpeedTestUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class SpeedTestManagerImpl: SpeedTestManager {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _downloadStatus = MutableStateFlow(TestStatus.NO_STARTED)
    private val _uploadStatus = MutableStateFlow(TestStatus.NO_STARTED)
    private val _downloadSpeed = MutableStateFlow(0.0)
    private val _uploadSpeed = MutableStateFlow(0.0)
    private var finishedDownloadSpeed: Double = 0.0
    private var finishedUploadSpeed: Double = 0.0

    override fun startDownloadTest() {
        val speedTestSocket = SpeedTestSocket()
        speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {
            override fun onCompletion(report: SpeedTestReport?) {
                finishedDownloadSpeed = convertBsToMbps(report?.transferRateBit)
                _downloadStatus.value = TestStatus.FINISHED
                speedTestSocket.clearListeners()
                speedTestSocket.closeSocket()
            }

            override fun onProgress(percent: Float, report: SpeedTestReport?) {
                Log.e("TAG", "onProgress: $percent - ${report?.progressPercent} - ${report?.transferRateBit}", )
                coroutineScope.launch {
                    _downloadSpeed.value = convertBsToMbps(report?.transferRateBit)
                }
            }

            override fun onError(speedTestError: SpeedTestError?, errorMessage: String?) {
                Log.e("TAG", "onError: $errorMessage ${speedTestError?.name}", )
                _downloadStatus.value = TestStatus.FAILED
                speedTestSocket.clearListeners()
                speedTestSocket.closeSocket()
            }

        })
        speedTestSocket.startDownload("http://eperf.comfortel.pro/speedtest/random3000x3000.jpg", 1000)
        _downloadStatus.value = TestStatus.STARTED
    }

    override fun startUploadTest() {
        val speedTestSocket = SpeedTestSocket()
        speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {
            override fun onCompletion(report: SpeedTestReport?) {

                finishedUploadSpeed = convertBsToMbps(report?.transferRateBit)
                _uploadStatus.value = TestStatus.FINISHED
                speedTestSocket.clearListeners()
                speedTestSocket.closeSocket()
            }

            override fun onProgress(percent: Float, report: SpeedTestReport?) {
                Log.e("TAG", "onProgress: $percent - ${report?.progressPercent} - ${report?.transferRateBit}", )
                coroutineScope.launch {
                    _uploadSpeed.value = convertBsToMbps(report?.transferRateBit)
                }
            }

            override fun onError(speedTestError: SpeedTestError?, errorMessage: String?) {
                Log.e("TAG", "onError: $errorMessage ${speedTestError?.name}", )
                _uploadStatus.value = TestStatus.FAILED
                speedTestSocket.clearListeners()
                speedTestSocket.closeSocket()
            }

        })
        speedTestSocket.startUpload("http://eperf.comfortel.pro/speedtest/upload.php", 1000000);
        _uploadStatus.value = TestStatus.STARTED

    }

    fun convertBsToMbps(value: BigDecimal?): Double {
        if (value == null) return 0.00
        return value.divide(BigDecimal("1000000"), 2, RoundingMode.HALF_UP).toDouble()
    }

    override fun getDownloadStatus(): Flow<Int> = _downloadStatus
    override fun getUploadStatus(): Flow<Int> = _uploadStatus

    override fun getDownloadSpeed(): Double = finishedDownloadSpeed

    override fun getCurrentDownloadSpeed(): Flow<Double> = _downloadSpeed

    override fun getUploadSpeed(): Double = finishedUploadSpeed

    override fun getCurrentUploadSpeed(): Flow<Double> = _uploadSpeed

}