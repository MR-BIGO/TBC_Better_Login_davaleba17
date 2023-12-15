package com.example.tbc_better_login_davaleba17.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_better_login_davaleba17.common.Resource
import com.example.tbc_better_login_davaleba17.datastore.PreferencesDataStore
import com.example.tbc_better_login_davaleba17.models.LoginResponse
import com.example.tbc_better_login_davaleba17.models.UserRequest
import com.example.tbc_better_login_davaleba17.network.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

class LoginFragmentViewModel : ViewModel() {

    private val _itemFlow = MutableStateFlow<Resource<LoginResponse>?>(null)
    val itemFlow: StateFlow<Resource<LoginResponse>?> = _itemFlow

    fun loginUser(user: UserRequest, remember: Boolean) {
        viewModelScope.launch {
            try {
                val response = Repository().loginUser(user)
                if (response.isSuccessful) {
                    _itemFlow.value = Resource.Success(response.body() ?: LoginResponse(""))

                    if (remember) {
                        PreferencesDataStore.saveEmailToken(user.email, response.body()!!.token)
                    }
                } else {
                    _itemFlow.value = Resource.Error("Error. ${response.errorBody()}")
                    Log.d("Response", "else: ${response.errorBody()}")
                }
            } catch (e: IOException) {
                _itemFlow.value = Resource.Error("Something went wrong: ${e.message}")
                Log.d("Response", "IOExeption, ${e.message}")
            } catch (e: Exception) {
                _itemFlow.value = Resource.Error("Other thing went wrong: ${e.message}")
                Log.d("Response", "Exeption, ${e.message}")
            }
        }
    }
}