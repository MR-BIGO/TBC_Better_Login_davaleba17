package com.example.tbc_better_login_davaleba17.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_better_login_davaleba17.common.Resource
import com.example.tbc_better_login_davaleba17.models.RegisterResponse
import com.example.tbc_better_login_davaleba17.models.UserRequest
import com.example.tbc_better_login_davaleba17.network.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

class RegisterFragmentViewModel : ViewModel(){

    private val _itemFlow = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val itemFlow: StateFlow<Resource<RegisterResponse>?> = _itemFlow

    fun registerUser(user: UserRequest){
        viewModelScope.launch {
            try {
                val response = Repository().registerUser(user)
                if (response.isSuccessful){
                    _itemFlow.value = Resource.Success(response.body() ?: RegisterResponse(-1, ""))
                }else{
                    _itemFlow.value = Resource.Error("Error. ${response.errorBody()}")
                    Log.d("Response", "else: ${response.errorBody()}")
                }
            }catch (e: IOException){
                _itemFlow.value = Resource.Error("Something went wrong: ${e.message}")
                Log.d("Response", "IOExeption, ${e.message}")
            }catch (e: Exception){
                //Don't really know exactly what type of exception I should be catching here
                _itemFlow.value = Resource.Error("Other thing went wrong: ${e.message}")
                Log.d("Response", "Exeption, ${e.message}")
            }
        }
    }
}