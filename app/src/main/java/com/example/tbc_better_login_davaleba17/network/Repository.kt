package com.example.tbc_better_login_davaleba17.network

import com.example.tbc_better_login_davaleba17.models.LoginResponse
import com.example.tbc_better_login_davaleba17.models.RegisterResponse
import com.example.tbc_better_login_davaleba17.models.UserRequest
import retrofit2.Response

class Repository {

    suspend fun registerUser(user: UserRequest): Response<RegisterResponse> {
        return RetrofitInstance.api.registerUser(user)
    }

    suspend fun loginUser(user: UserRequest): Response<LoginResponse> {
        return RetrofitInstance.api.loginUser(user)
    }
}