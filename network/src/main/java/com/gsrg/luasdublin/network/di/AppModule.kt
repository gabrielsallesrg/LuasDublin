package com.gsrg.luasdublin.network.di

import android.content.Context
import com.gsrg.luasdublin.domain.BuildConfig
import com.gsrg.luasdublin.network.MockInterceptor
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

    @Singleton
    @Provides
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
    fun provideLuasApiService(client: OkHttpClient): com.gsrg.luasdublin.network.api.LuasApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.LUAS_BASE_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(com.gsrg.luasdublin.network.api.LuasApiService::class.java)
    }
}