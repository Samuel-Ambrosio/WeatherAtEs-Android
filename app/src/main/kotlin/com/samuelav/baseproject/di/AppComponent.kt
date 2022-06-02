package com.samuelav.baseproject.di

import com.samuelav.data.local.di.localModule
import com.samuelav.data.remote.di.remoteModule
import com.samuelav.data.repository.di.repositoryModule
import com.samuelav.domain.di.domainModule
import com.samuelav.feature.details.di.detailsFeatureModule
import com.samuelav.features.home.di.homeFeatureModule
import com.samuelav.features.search.di.searchFeatureModule
import com.samuelav.features.settings.di.settingsFeatureModule

val appComponent =
    listOf(
        appModule,
        localModule,
        remoteModule,
        repositoryModule,
        domainModule,
        homeFeatureModule,
        detailsFeatureModule,
        searchFeatureModule,
        settingsFeatureModule
    )