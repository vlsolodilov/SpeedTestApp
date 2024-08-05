package com.solodilov.util.di

import com.solodilov.util.StringResourceProvider
import org.koin.dsl.module

val utilModule = module {
    single { StringResourceProvider(context = get()) }
}