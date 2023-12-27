package com.example.tbc_better_login_davaleba17.di

import com.example.tbc_better_login_davaleba17.data.common.Constants.Companion.BASE_URL
import com.example.tbc_better_login_davaleba17.data.login.ILoginService
import com.example.tbc_better_login_davaleba17.data.register.IRegisterService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build()
    }

    @Singleton
    @Provides
    fun provideLogInService(retrofit: Retrofit): ILoginService {
        return retrofit.create(ILoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterService(retrofit: Retrofit): IRegisterService {
        return retrofit.create(IRegisterService::class.java)
    }
}