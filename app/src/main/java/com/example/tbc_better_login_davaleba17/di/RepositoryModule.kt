package com.example.tbc_better_login_davaleba17.di

import com.example.tbc_better_login_davaleba17.data.service.ILoginService
import com.example.tbc_better_login_davaleba17.data.repository.LoginRepositoryImpl
import com.example.tbc_better_login_davaleba17.data.service.IRegisterService
import com.example.tbc_better_login_davaleba17.data.repository.RegisterRepositoryImpl
import com.example.tbc_better_login_davaleba17.domain.repository.ILogInRepository
import com.example.tbc_better_login_davaleba17.domain.repository.IRegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: ILoginService): ILogInRepository {
        return LoginRepositoryImpl(loginService)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(registerService: IRegisterService): IRegisterRepository {
        return RegisterRepositoryImpl(registerService)
    }
}