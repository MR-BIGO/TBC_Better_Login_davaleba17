package com.example.tbc_better_login_davaleba17.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_better_login_davaleba17.domain.DataStoreUtil
import com.example.tbc_better_login_davaleba17.domain.repository.IDatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val datastore: IDatastoreRepository) : ViewModel() {

    fun clearSession(){
        viewModelScope.launch {
            datastore.clearString(DataStoreUtil.EMAIL)
        }
    }
}