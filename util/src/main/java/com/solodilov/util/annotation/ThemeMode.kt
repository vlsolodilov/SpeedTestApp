package com.solodilov.util.annotation

import androidx.annotation.IntDef
import com.solodilov.util.annotation.ThemeMode.Companion.SYSTEM
import com.solodilov.util.annotation.ThemeMode.Companion.LIGHT
import com.solodilov.util.annotation.ThemeMode.Companion.DARK

@IntDef(SYSTEM, LIGHT, DARK)
@Retention(AnnotationRetention.SOURCE)
annotation class ThemeMode {
    companion object {
        const val SYSTEM = 0
        const val LIGHT = 1
        const val DARK = 2
    }
}
