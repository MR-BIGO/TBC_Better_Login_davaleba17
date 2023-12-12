package com.example.tbc_better_login_davaleba17.network

import com.example.tbc_better_login_davaleba17.models.LoginResponse
import com.example.tbc_better_login_davaleba17.models.RegisterResponse
import com.example.tbc_better_login_davaleba17.models.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IApiService {

    @POST("register")
    suspend fun registerUser(@Body user: UserRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(@Body user: UserRequest): Response<LoginResponse>
}