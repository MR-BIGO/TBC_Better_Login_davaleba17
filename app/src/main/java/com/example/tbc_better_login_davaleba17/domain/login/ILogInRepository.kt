package com.example.tbc_better_login_davaleba17.domain.login

import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface ILogInRepository {
    suspend fun logIn(@Body user: UserRequest): Flow<Resource<LoginResponse>>
}