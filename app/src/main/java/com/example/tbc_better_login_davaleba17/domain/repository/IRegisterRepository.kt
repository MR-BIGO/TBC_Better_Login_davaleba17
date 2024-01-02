package com.example.tbc_better_login_davaleba17.domain.repository

import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import com.example.tbc_better_login_davaleba17.domain.model.RegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface IRegisterRepository {
    suspend fun registerUser(@Body user: UserRequest): Flow<Resource<RegisterResponse>>
}