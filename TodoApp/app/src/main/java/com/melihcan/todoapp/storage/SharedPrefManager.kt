package com.melihcan.todoapp.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.melihcan.todoapp.model.GetUserModel
import com.melihcan.todoapp.model.ListModel
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

    val user: GetUserModel
        get() {
            val shredPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return GetUserModel(
                shredPref.getString("useruuid", null).toString(),
                shredPref.getString("username", null).toString()
            )
        }

    fun getLists(key: String): List<ListModel> {
            val shredPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val json = shredPref.getString(key, null)
            val type = object : TypeToken<List<ListModel>>() {}.type

            return Gson().fromJson(json, type) ?: emptyList()
        }

    fun saveToken(data: LoginResponseModel) {
        val sharedPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("token", data.token)
        editor.apply()
    }

    fun saveUser(data: GetUserModel) {
        val sharedPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("useruuid", data.uuid)
        editor.putString("username", data.username)
        editor.apply()
    }

    fun saveList(data: List<ListModel>) {
        val shredPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = shredPref.edit()
        val json = Gson().toJson(data)

        editor.putString(shredPref.getString("useruuid", null), json)
        editor.apply()
    }

    fun clear() {
        val sharedPref = sharedContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear().commit()
        editor.apply()
    }
}