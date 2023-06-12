package com.melihcan.todoapp.storage

import android.content.Context
import com.melihcan.todoapp.model.GetUserModel
import com.melihcan.todoapp.model.LoginRequestModel
import com.melihcan.todoapp.model.LoginResponseModel

class SharedPrefManager private constructor(private val sharedContext: Context){

    companion object{
        private val SHARED_PREF_NAME = "todo_app_shared_prefs"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

    val token: LoginResponseModel
        get() {
            val shredPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return LoginResponseModel(
                shredPref.getString("token", null).toString()
            )
        }

    fun saveToken(data: LoginResponseModel) {
        val sharedPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("token", data.token)
        editor.apply()
    }

    fun clear() {
        val sharedPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}