package com.segunfrancis.remote.di

import com.segunfrancis.remote.BuildConfig
import com.segunfrancis.remote.util.RemoteConstants.BASE_URL
import com.segunfrancis.remote.util.RemoteConstants.CONNECTION_TIMEOUT
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private val provideMoshi: Moshi
        get() =
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val provideLoggingInterceptor: HttpLoggingInterceptor
        get() =
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

    private val provideClient: OkHttpClient
        get() {
            return OkHttpClient()
                .newBuilder()
                .callTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(provideLoggingInterceptor)
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .build()
                    )
                }
                .build()
        }

    @Volatile
    private var retrofitInstance: Retrofit? = null

    @Provides
    @Singleton
    fun provideService(): Retrofit {
        return retrofitInstance ?: synchronized(this) {
            retrofitInstance ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideClient)
                .addConverterFactory(MoshiConverterFactory.create(provideMoshi))
                .build()
        }
    }
}
