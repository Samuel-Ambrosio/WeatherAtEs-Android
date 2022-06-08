package com.samuelav.data.model.weather.mock

import com.samuelav.data.model.weather.*
import java.time.LocalDateTime

val weatherOneCallBOMock =
    WeatherOneCallBO(
        location = "Madrid",
        lat = 41.3828,
        lon = 2.107,
        timeZone = "Europe/Madrid",
        timeZoneOffset = 7200,
        current =
            CurrentWeatherBO(
                dateTime = LocalDateTime.now(),
                sunriseDateTime = LocalDateTime.now(),
                sunsetDateTime = LocalDateTime.now(),
                temp = 21.42,
                feelsLike = 21.5,
                pressure = 1015,
                humidity = 72,
                dewPoint = 16.17,
                uvi = 0.0,
                clouds = 20,
                visibility = 10000,
                windSpeed = 2.57,
                windDeg = 60.0,
                state =
                    WeatherStateBO(
                        id = 801,
                        main = "Clouds",
                        description = "algo de nubes",
                        icon = "02n"
                    )
            ),
        hourly =
            listOf(
                HourlyWeatherBO(
                    dateTime = LocalDateTime.now(),
                    temp = 21.51,
                    feelsLike = 21.6,
                    pressure = 1015,
                    humidity = 72,
                    dewPoint = 16.25,
                    uvi = 0.0,
                    clouds = 36,
                    visibility = 10000,
                    windSpeed = 3.3,
                    windDeg = 150.0,
                    windGust = 4.65,
                    state =
                        WeatherStateBO(
                            id = 802,
                            main = "Clouds",
                            description = "nubes dispersas",
                            icon = "03n"
                        )
                ),
                HourlyWeatherBO(
                    dateTime = LocalDateTime.now().plusHours(1),
                    temp = 21.51,
                    feelsLike = 21.6,
                    pressure = 1015,
                    humidity = 72,
                    dewPoint = 16.25,
                    uvi = 0.0,
                    clouds = 36,
                    visibility = 10000,
                    windSpeed = 3.3,
                    windDeg = 150.0,
                    windGust = 4.65,
                    state =
                        WeatherStateBO(
                            id = 802,
                            main = "Clouds",
                            description = "nubes dispersas",
                            icon = "03n"
                        )
                ),
                HourlyWeatherBO(
                    dateTime = LocalDateTime.now().plusHours(2),
                    temp = 21.51,
                    feelsLike = 21.6,
                    pressure = 1015,
                    humidity = 72,
                    dewPoint = 16.25,
                    uvi = 0.0,
                    clouds = 36,
                    visibility = 10000,
                    windSpeed = 3.3,
                    windDeg = 150.0,
                    windGust = 4.65,
                    state =
                        WeatherStateBO(
                            id = 802,
                            main = "Clouds",
                            description = "nubes dispersas",
                            icon = "03n"
                        )
                ),
                HourlyWeatherBO(
                    dateTime = LocalDateTime.now().plusHours(3),
                    temp = 21.51,
                    feelsLike = 21.6,
                    pressure = 1015,
                    humidity = 72,
                    dewPoint = 16.25,
                    uvi = 0.0,
                    clouds = 36,
                    visibility = 10000,
                    windSpeed = 3.3,
                    windDeg = 150.0,
                    windGust = 4.65,
                    state =
                        WeatherStateBO(
                            id = 802,
                            main = "Clouds",
                            description = "nubes dispersas",
                            icon = "03n"
                        )
                )
            ),
        daily =
            listOf(
                DailyWeatherBO(
                    dateTime = LocalDateTime.now(),
                    sunriseDateTime = LocalDateTime.now(),
                    sunsetDateTime = LocalDateTime.now(),
                    moonriseDateTime = LocalDateTime.now(),
                    moonsetDateTime = LocalDateTime.now(),
                    moonPhase = 0.28,
                    temp =
                        DailyWeatherTempBO(
                            day = 25.45,
                            min = 21.38,
                            max = 25.7,
                            night = 21.42,
                            eve = 23.05,
                            morn = 21.38
                        ),
                    feelsLike =
                        DailyWeatherFeelsLikeBO(
                            day = 25.57,
                            night = 21.5,
                            eve = 23.11,
                            morn = 21.32
                        ),
                    pressure = 1012,
                    humidity = 58,
                    dewPoint = 15.5,
                    uvi = 8.29,
                    clouds = 100,
                    windSpeed = 6.36,
                    windDeg = 102.0,
                    windGust = 7.83,
                    state =
                        WeatherStateBO(
                            id = 804,
                            main = "Clouds",
                            description = "nubes",
                            icon = "04d"
                        )
                ),
                DailyWeatherBO(
                    dateTime = LocalDateTime.now().plusDays(1),
                    sunriseDateTime = LocalDateTime.now().plusDays(1),
                    sunsetDateTime = LocalDateTime.now().plusDays(1),
                    moonriseDateTime = LocalDateTime.now().plusDays(1),
                    moonsetDateTime = LocalDateTime.now().plusDays(1),
                    moonPhase = 0.28,
                    temp =
                        DailyWeatherTempBO(
                            day = 25.45,
                            min = 21.38,
                            max = 25.7,
                            night = 21.42,
                            eve = 23.05,
                            morn = 21.38
                        ),
                    feelsLike =
                        DailyWeatherFeelsLikeBO(
                            day = 25.57,
                            night = 21.5,
                            eve = 23.11,
                            morn = 21.32
                        ),
                    pressure = 1012,
                    humidity = 58,
                    dewPoint = 15.5,
                    uvi = 8.29,
                    clouds = 100,
                    windSpeed = 6.36,
                    windDeg = 102.0,
                    windGust = 7.83,
                    state =
                        WeatherStateBO(
                            id = 804,
                            main = "Clouds",
                            description = "nubes",
                            icon = "04d"
                        )
                ),
                DailyWeatherBO(
                    dateTime = LocalDateTime.now().plusDays(2),
                    sunriseDateTime = LocalDateTime.now().plusDays(2),
                    sunsetDateTime = LocalDateTime.now().plusDays(2),
                    moonriseDateTime = LocalDateTime.now().plusDays(2),
                    moonsetDateTime = LocalDateTime.now().plusDays(2),
                    moonPhase = 0.28,
                    temp =
                        DailyWeatherTempBO(
                            day = 25.45,
                            min = 21.38,
                            max = 25.7,
                            night = 21.42,
                            eve = 23.05,
                            morn = 21.38
                        ),
                    feelsLike =
                        DailyWeatherFeelsLikeBO(
                            day = 25.57,
                            night = 21.5,
                            eve = 23.11,
                            morn = 21.32
                        ),
                    pressure = 1012,
                    humidity = 58,
                    dewPoint = 15.5,
                    uvi = 8.29,
                    clouds = 100,
                    windSpeed = 6.36,
                    windDeg = 102.0,
                    windGust = 7.83,
                    state =
                        WeatherStateBO(
                            id = 804,
                            main = "Clouds",
                            description = "nubes",
                            icon = "04d"
                        )
                ),
                DailyWeatherBO(
                    dateTime = LocalDateTime.now().plusDays(3),
                    sunriseDateTime = LocalDateTime.now().plusDays(3),
                    sunsetDateTime = LocalDateTime.now().plusDays(3),
                    moonriseDateTime = LocalDateTime.now().plusDays(3),
                    moonsetDateTime = LocalDateTime.now().plusDays(3),
                    moonPhase = 0.28,
                    temp =
                        DailyWeatherTempBO(
                            day = 25.45,
                            min = 21.38,
                            max = 25.7,
                            night = 21.42,
                            eve = 23.05,
                            morn = 21.38
                        ),
                    feelsLike =
                        DailyWeatherFeelsLikeBO(
                            day = 25.57,
                            night = 21.5,
                            eve = 23.11,
                            morn = 21.32
                        ),
                    pressure = 1012,
                    humidity = 58,
                    dewPoint = 15.5,
                    uvi = 8.29,
                    clouds = 100,
                    windSpeed = 6.36,
                    windDeg = 102.0,
                    windGust = 7.83,
                    state =
                        WeatherStateBO(
                            id = 804,
                            main = "Clouds",
                            description = "nubes",
                            icon = "04d"
                        )
                )
            ),
        weatherUnit = WeatherUnit.Metric
    )