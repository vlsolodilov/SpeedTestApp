package com.solodilov.data.di

import android.content.Context
import android.content.SharedPreferences
import com.solodilov.data.repository.InternetCheckImpl
import com.solodilov.data.repository.SettingsManagerImpl
import com.solodilov.data.repository.SpeedTestManagerImpl
import com.solodilov.domain.repository.InternetCheck
import com.solodilov.domain.repository.SettingsManager
import com.solodilov.domain.repository.SpeedTestManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SPEED_TEST_PREFS = "SpeedTestPrefs"

val dataModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences(SPEED_TEST_PREFS, Context.MODE_PRIVATE)
    }

    single<InternetCheck> { InternetCheckImpl(androidContext()) }

    single<SettingsManager> { SettingsManagerImpl(pref = get()) }

    single<SpeedTestManager> { SpeedTestManagerImpl(androidContext()) }

}