package com.solodilov.data.repository

import android.content.Context
import android.util.Log
import com.solodilov.domain.repository.SpeedTestManager
import com.solodilov.util.annotation.TestStatus
import com.speedchecker.android.sdk.Public.SpeedTestListener
import com.speedchecker.android.sdk.Public.SpeedTestResult
import com.speedchecker.android.sdk.SpeedcheckerSDK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SpeedTestManagerImpl(
    private val context: Context,
    ): SpeedTestManager, SpeedTestListener  {
    private val coroutineScope =  CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _status = MutableStateFlow(TestStatus.NO_STARTED)
    private val _downloadSpeed = MutableStateFlow(0.0)
    private val _uploadSpeed = MutableStateFlow(0.0)
    private var finishedDownloadSpeed: Double = 0.0
    private var finishedUploadSpeed: Double = 0.0

    init {
        SpeedcheckerSDK.init(context)
        SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(this)
    }

    override fun startTest() {
        SpeedcheckerSDK.SpeedTest.startTest(context)
    }

    override fun getStatus(): Flow<Int> = _status

    override fun getDownloadSpeed(): Double = finishedDownloadSpeed

    override fun getCurrentDownloadSpeed(): Flow<Double> = _downloadSpeed

    override fun getUploadSpeed(): Double = finishedUploadSpeed

    override fun getCurrentUploadSpeed(): Flow<Double> = _uploadSpeed

    override fun onTestStarted() {
        Log.e("TAG", "onTestStarted: ", )
        _status.value = TestStatus.STARTED
        finishedDownloadSpeed = 0.0
        finishedUploadSpeed = 0.0
    }

    override fun onFetchServerFailed(p0: Int?) {
        _status.value = TestStatus.FAILED
    }

    override fun onFindingBestServerStarted() {}

    override fun onTestFinished(p0: SpeedTestResult?) {
        _status.value = TestStatus.FINISHED
    }

    override fun onPingStarted() {}

    override fun onPingFinished(p0: Int, p1: Int) {}

    override fun onDownloadTestStarted() {}

    override fun onDownloadTestProgress(p0: Int, p1: Double, p2: Double) {
        Log.e("TAG", "onDownloadTestProgress: $p0 $p1 $p2", )
        coroutineScope.launch {
            _downloadSpeed.value = p1
        }
    }

    override fun onDownloadTestFinished(p0: Double) {
        finishedDownloadSpeed = p0
    }

    override fun onUploadTestStarted() {}


    override fun onUploadTestProgress(p0: Int, p1: Double, p2: Double) {
        _uploadSpeed.value = p1
    }

    override fun onUploadTestFinished(p0: Double) {
        finishedUploadSpeed = p0
    }

    override fun onTestWarning(p0: String?) {
        _status.value = TestStatus.FAILED
    }

    override fun onTestFatalError(p0: String?) {
        _status.value = TestStatus.FAILED
    }

    override fun onTestInterrupted(p0: String?) {
        _status.value = TestStatus.FAILED
    }
}