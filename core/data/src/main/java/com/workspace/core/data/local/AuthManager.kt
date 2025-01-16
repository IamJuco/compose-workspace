package com.workspace.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    companion object {
//        private val USER_ID_TOKEN = stringPreferencesKey("user_id_token")
        private val TEMP_USER_EMAIL = stringPreferencesKey("temp_user_email")
    }

//    suspend fun saveIdToken(idToken: String) {
//        dataStore.edit { preferences ->
//            preferences[USER_ID_TOKEN] = idToken
//        }
//    }
//
//    val idToken: Flow<String?> = dataStore.data
//        .map { preferences -> preferences[USER_ID_TOKEN] }

    suspend fun saveTempUserEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[TEMP_USER_EMAIL] = email
        }
    }

    val tempUserEmail: Flow<String?> = dataStore.data
        .map { preferences -> preferences[TEMP_USER_EMAIL] }

    suspend fun clearData() {
        dataStore.edit { it.clear() }
    }
}