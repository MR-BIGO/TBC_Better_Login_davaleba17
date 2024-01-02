package com.example.tbc_better_login_davaleba17.presentation.splash

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_better_login_davaleba17.domain.DataStoreUtil
import com.example.tbc_better_login_davaleba17.domain.repository.IDatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(private val datastoreRepository: IDatastoreRepository) :
    ViewModel() {

    private val _itemFlow = MutableSharedFlow<SplashNavigationEvent>()
    val itemFlow = _itemFlow.asSharedFlow()

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            datastoreRepository.readString(DataStoreUtil.EMAIL).collect {
                d("dataStoreEmail", it)
                _itemFlow.emit(

                    if (it.isEmpty()) SplashNavigationEvent.NavigationLogin
                    else SplashNavigationEvent.NavigationHome(it)
                )
            }
        }
    }

    sealed class SplashNavigationEvent {
        data class NavigationHome(val email: String) : SplashNavigationEvent()
        data object NavigationLogin : SplashNavigationEvent()
    }
}