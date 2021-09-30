package com.convity.utility

import android.content.Context
import android.preference.PreferenceManager
import com.convity.application.ConvityApp
import com.lacronicus.easydatastorelib.BooleanEntry
import com.lacronicus.easydatastorelib.DatastoreBuilder
import com.lacronicus.easydatastorelib.Preference
import com.lacronicus.easydatastorelib.StringEntry

interface PreferenceStore {

    @get:Preference("isLogin")
    val isLogin: BooleanEntry?

    @get:Preference("token")
    val token: StringEntry?

    companion object {
        private fun create(context: Context?): PreferenceStore {
            return DatastoreBuilder(PreferenceManager.getDefaultSharedPreferences(context)).create(
                PreferenceStore::class.java
            )
        }

        fun removeAll(context: Context?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply()
        }

        val prefs = create(ConvityApp.getInstance())
    }
}