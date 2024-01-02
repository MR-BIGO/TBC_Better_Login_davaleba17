package com.example.tbc_better_login_davaleba17.data.service

import com.example.tbc_better_login_davaleba17.domain.model.RegisterResponse
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IRegisterService {
    @POST("register")
    suspend fun registerUser(@Body user: UserRequest): Response<RegisterResponse>
}