package com.example.tbc_better_login_davaleba17.data.repository

import android.util.Log.d
import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import com.example.tbc_better_login_davaleba17.data.service.ILoginService
import com.example.tbc_better_login_davaleba17.domain.repository.ILogInRepository
import com.example.tbc_better_login_davaleba17.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(val logInService: ILoginService) : ILogInRepository {
    override suspend fun logIn(user: UserRequest): Flow<Resource<LoginResponse>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val response = logInService.loginUser(user)
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    d("LogInImplResponse", "${response.errorBody()}")
                    emit(Resource.Error(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                d("LogInImplResponse", "${e.message}")
                emit(Resource.Error(e.message.toString()))
            }
            emit(Resource.Loading(false))
        }
    }
}