package com.samuelav.data.remote.di

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.samuelav.data.remote.BuildConfig
import com.samuelav.data.remote.BuildConfig.API_BASE_PATH
import com.samuelav.data.remote.BuildConfig.API_KEY
import com.samuelav.data.remote.location.LocationDataSourceImpl
import com.samuelav.data.remote.search.SearchLocationRemoteDataSourceImpl
import com.samuelav.data.remote.utils.ResultCallAdapterFactory
import com.samuelav.data.remote.weather.WeatherRemoteDataSourceImpl
import com.samuelav.data.remote.weather.WeatherWs
import com.samuelav.data.source.location.LocationDataSource
import com.samuelav.data.source.search.SearchLocationRemoteDataSource
import com.samuelav.data.source.weather.WeatherRemoteDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    single { interceptorProvider() }
    single { okHttpClientProvider(get()) }
    single { gsonProvider() }
    single { retrofitProvider(get(), get()) }
    single { Geocoder(get()) }
    single { androidApplication().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    single<LocationDataSource> { LocationDataSourceImpl(get(), get()) }
    single { weatherWsProvider(get()) }
    single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get(), get()) }
    single<SearchLocationRemoteDataSource> { SearchLocationRemoteDataSourceImpl(get()) }
}

/**
 *  Retrofit configuration
 */
private fun interceptorProvider(): Interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun okHttpClientProvider(interceptor: Interceptor) =
    OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) { addInterceptor(interceptor) }
        addInterceptor { chain ->
            val url = chain
                .request()
                .url
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }
    }.build()

private fun gsonProvider() =
    GsonBuilder()
        .setDateFormat("dd/MM/yyyy hh:mm")
        .create()

private fun retrofitProvider(okHttpClient: OkHttpClient, gson: Gson) =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(API_BASE_PATH)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .build()

/**
 *  Service providers
 */
private fun weatherWsProvider(retrofit: Retrofit) = retrofit.create(WeatherWs::class.java)