package com.example.tbc_better_login_davaleba17.data.login

import com.example.tbc_better_login_davaleba17.domain.login.LoginResponse
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ILoginService {
    @POST("login")
    suspend fun loginUser(@Body user: UserRequest): Response<LoginResponse>
}