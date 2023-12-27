package com.example.tbc_better_login_davaleba17.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import com.example.tbc_better_login_davaleba17.domain.login.ILogInRepository
import com.example.tbc_better_login_davaleba17.domain.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(val logInRepository: ILogInRepository) :
    ViewModel() {

    private val _itemFlow = MutableStateFlow<Resource<LoginResponse>?>(null)
    val itemFlow: StateFlow<Resource<LoginResponse>?> = _itemFlow.asStateFlow()

    fun loginUser(user: UserRequest, remember: Boolean) {
        viewModelScope.launch {
            logInRepository.logIn(user).collect {
                when (it) {
                    is Resource.Success -> _itemFlow.value = Resource.Success(it.data)
                    is Resource.Error -> _itemFlow.value = Resource.Error(it.errorMessage)
                    is Resource.Loading -> _itemFlow.value = Resource.Loading(it.loading)
                }
            }
        }
    }
}