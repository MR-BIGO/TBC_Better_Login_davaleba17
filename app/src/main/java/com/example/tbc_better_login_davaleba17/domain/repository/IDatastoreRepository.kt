package com.example.tbc_better_login_davaleba17.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface IDatastoreRepository {

    suspend fun saveString(key: Preferences.Key<String>, value: String)
    fun readString(key: Preferences.Key<String>): Flow<String>
    suspend fun clearString(key: Preferences.Key<String>)
}