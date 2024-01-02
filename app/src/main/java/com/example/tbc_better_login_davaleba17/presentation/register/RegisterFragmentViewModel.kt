package com.example.tbc_better_login_davaleba17.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import com.example.tbc_better_login_davaleba17.domain.repository.IRegisterRepository
import com.example.tbc_better_login_davaleba17.domain.model.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(private val registerRepository: IRegisterRepository): ViewModel(){

    private val _itemFlow = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val itemFlow: StateFlow<Resource<RegisterResponse>?> = _itemFlow.asStateFlow()

    fun registerUser(user: UserRequest){
        viewModelScope.launch {
            registerRepository.registerUser(user).collect{
                when(it){
                    is Resource.Success -> _itemFlow.value = Resource.Success(it.data)
                    is Resource.Error -> _itemFlow.value = Resource.Error(it.errorMessage)
                    is Resource.Loading -> _itemFlow.value = Resource.Loading(it.loading)
                }
            }
        }
    }
}