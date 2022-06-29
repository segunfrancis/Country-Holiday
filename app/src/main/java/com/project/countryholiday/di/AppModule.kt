package com.project.countryholiday.di

import com.google.gson.Gson
import com.project.countryholiday.BuildConfig
import com.project.countryholiday.data.HolidayService
import com.project.countryholiday.repository.HolidayRepository
import com.project.countryholiday.repository.HolidayRepositoryImpl
import com.project.countryholiday.util.BASE_URL
import com.project.countryholiday.util.CONNECTION_TIMEOUT
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<Gson> { Gson().newBuilder().setLenient().create() }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        OkHttpClient()
            .newBuilder()
            .callTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor = get<HttpLoggingInterceptor>())
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader(name = "Authorization", value = "Bearer ${BuildConfig.M3O_API_KEY}")
                        .build()
                )
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(HolidayService::class.java)
    }

    factory<HolidayRepository> { HolidayRepositoryImpl(api = get(), dispatcher = get()) }

    single { Dispatchers.IO }
}
