package com.gsrg.luasdublin.domain.di

import android.content.Context
import com.gsrg.luasdublin.domain.BuildConfig
import com.gsrg.luasdublin.domain.MockInterceptor
import com.gsrg.luasdublin.domain.api.LuasApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideClient(@ApplicationContext applicationContext: Context): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder().apply {
            addInterceptor(
                if (BuildConfig.MOCK_RESPONSE) {
                    MockInterceptor(applicationContext)
                } else {
                    logger
                }
            )
        }.build()
    }

    @Singleton
    @Provides
    fun provideLuasApiService(client: OkHttpClient): LuasApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.LUAS_BASE_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LuasApiService::class.java)
    }
}