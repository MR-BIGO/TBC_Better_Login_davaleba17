package com.example.tbc_better_login_davaleba17.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tbc_better_login_davaleba17.MyApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

object PreferencesDataStore {

    private val EMAIL = stringPreferencesKey("email")
    private val TOKEN = stringPreferencesKey("key")

    suspend fun saveEmailToken(email: String, token: String) {
        MyApplication.application.applicationContext.dataStore.edit { preferences ->
            preferences[EMAIL] = email
            preferences[TOKEN] = token
        }
    }

    fun getEmail(): Flow<String> {
        return MyApplication.application.applicationContext.dataStore.data
            .map { preferences ->
                preferences[EMAIL] ?: ""
            }
    }

    fun getToken(): Flow<String> {
        return MyApplication.application.applicationContext.dataStore.data
            .map { preferences ->
                preferences[TOKEN] ?: ""
            }
    }

    suspend fun clearSession() {
        MyApplication.application.applicationContext.dataStore.edit { preferences ->
            preferences.remove(EMAIL)
            preferences.remove(TOKEN)
        }
    }
}