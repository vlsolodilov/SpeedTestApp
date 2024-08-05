package com.solodilov.util

import android.content.Context
import androidx.annotation.StringRes

class StringResourceProvider(private val context: Context) {

    fun getString(@StringRes resourceId: Int, vararg arguments: Any): String =
        context.getString(resourceId, *arguments)

}