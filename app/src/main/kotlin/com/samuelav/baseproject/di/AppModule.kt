package com.samuelav.baseproject.di

import com.samuelav.common.app.AppCommonConfiguration
import com.samuelav.commonandroid.app.configuration.AppConfiguration
import org.koin.dsl.module

val appModule = module {
    single { AppCommonConfiguration() }
    single { AppConfiguration() }
}