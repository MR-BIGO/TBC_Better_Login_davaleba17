package com.example.tbc_better_login_davaleba17.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbc_better_login_davaleba17.domain.DataStoreUtil
import com.example.tbc_better_login_davaleba17.domain.repository.IDatastoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreRepositoryImpl @Inject constructor(private val datastore: DataStore<Preferences>) :
    IDatastoreRepository {
    override suspend fun saveString(key: Preferences.Key<String>, value: String) {
        datastore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun readString(key: Preferences.Key<String>): Flow<String> =
        datastore.data.map { preferences ->
            preferences[DataStoreUtil.EMAIL] ?: ""
        }

    override suspend fun clearString(key: Preferences.Key<String>) {
        datastore.edit { preferences ->
            preferences.remove(key)
        }
    }
}