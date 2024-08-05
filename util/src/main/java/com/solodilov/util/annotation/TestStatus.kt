package com.solodilov.util.annotation

import androidx.annotation.IntDef
import com.solodilov.util.annotation.TestStatus.Companion.FAILED
import com.solodilov.util.annotation.TestStatus.Companion.FINISHED
import com.solodilov.util.annotation.TestStatus.Companion.NO_STARTED
import com.solodilov.util.annotation.TestStatus.Companion.STARTED

@IntDef(NO_STARTED, STARTED, FINISHED, FAILED)
@Retention(AnnotationRetention.SOURCE)
annotation class TestStatus {
    companion object {
        const val NO_STARTED = 0
        const val STARTED = 1
        const val FINISHED = 2
        const val FAILED = 3
    }
}
