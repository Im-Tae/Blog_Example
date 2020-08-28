@file:Suppress("DEPRECATION")

package com.imtae.securitylibraryexample

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PreferenceManager(context: Context) {

    private val pref: SharedPreferences by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        EncryptedSharedPreferences.create(
            "SecurityLibraryExample",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getData(key: String): String? = pref.getString(key, null)

    fun setData(key : String, value : String) = pref.edit().putString(key, value).commit()
}