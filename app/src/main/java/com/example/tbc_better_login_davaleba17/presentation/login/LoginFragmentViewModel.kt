package com.example.tbc_better_login_davaleba17.presentation.login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import com.example.tbc_better_login_davaleba17.domain.DataStoreUtil
import com.example.tbc_better_login_davaleba17.domain.repository.ILogInRepository
import com.example.tbc_better_login_davaleba17.domain.model.LoginResponse
import com.example.tbc_better_login_davaleba17.domain.repository.IDatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(private val logInRepository: ILogInRepository, private val datastore: IDatastoreRepository)
     :
    ViewModel() {

    private val _itemFlow = MutableStateFlow<Resource<LoginResponse>?>(null)
    val itemFlow: StateFlow<Resource<LoginResponse>?> = _itemFlow.asStateFlow()

    fun loginUser(user: UserRequest, remember: Boolean) {
        viewModelScope.launch {
            logInRepository.logIn(user).collect {
                when (it) {
                    is Resource.Success -> {
                        if (remember) {
                            datastore.saveString(DataStoreUtil.EMAIL, user.email)
                        }

                        //d("datastoreEmail", datastore.readString(DataStoreUtil.EMAIL).last())
                        _itemFlow.value = Resource.Success(it.data)

                    }
                    is Resource.Error -> _itemFlow.value = Resource.Error(it.errorMessage)
                    is Resource.Loading -> _itemFlow.value = Resource.Loading(it.loading)
                }
            }
        }
    }
}