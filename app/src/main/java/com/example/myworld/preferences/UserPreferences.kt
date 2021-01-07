package com.example.myworld.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "user_data_store"
        )
    }

//    val userToken: Flow<String?>
//        get() = dataStore.data.map { preferences ->
//            preferences[LOGIN_USER_TOKEN]
//        }

    val userId: Flow<Int?>
        get() = dataStore.data.map { preferences ->
            preferences[LOGIN_USER_ID]
        }

    suspend fun saveLoginUserAuthToken(token: String, user_id: Int) {
        dataStore.edit { preferences ->
            preferences[LOGIN_USER_TOKEN] = token
            preferences[LOGIN_USER_ID] = user_id
        }
    }

    suspend fun saveSignUpUserAuthToken(token: String, email: String, id: Int, username: String) {
        dataStore.edit { preferences ->
            preferences[SIGN_UP_USER_TOKEN] = token
            preferences[SIGN_UP_USER_EMAIL] = email
            preferences[SIGN_UP_USER_ID] = id
            preferences[SIGN_UP_USERNAME] = username
        }
    }


//    suspend fun clear() {
//        dataStore.edit { preferences ->
//            preferences.clear()
//        }
//    }

    companion object {
        private val LOGIN_USER_TOKEN = preferencesKey<String>("user_token")
        private val LOGIN_USER_ID = preferencesKey<Int>("user_id")

        private val SIGN_UP_USER_TOKEN = preferencesKey<String>("token")
        private val SIGN_UP_USER_EMAIL = preferencesKey<String>("email")
        private val SIGN_UP_USER_ID = preferencesKey<Int>("id")
        private val SIGN_UP_USERNAME = preferencesKey<String>("username")
    }

}