package com.example.tbc_better_login_davaleba17.data.register

import android.util.Log.d
import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import com.example.tbc_better_login_davaleba17.domain.register.IRegisterRepository
import com.example.tbc_better_login_davaleba17.domain.register.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(val registerService: IRegisterService) :
    IRegisterRepository {
    override suspend fun registerUser(user: UserRequest): Flow<Resource<RegisterResponse>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val response = registerService.registerUser(user)
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