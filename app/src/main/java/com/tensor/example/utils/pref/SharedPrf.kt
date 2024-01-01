package com.tensor.example.utils.pref
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tensor.example.R
import com.tensor.example.data.fireatstore.model.User
class SharedPrf  constructor( context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val USER = "USER"
    fun getStoredTag(str: String): String {
        return prefs.getString(str, "")!!
    }

    fun setStoredTag(str: String, query: String) {
        prefs.edit().putString(str, query).apply()
    }

    fun getUser(id:String): User {
            val gson = Gson()
            val json: String? = prefs.getString(id, "")
            val obj:User = gson.fromJson(json,User::class.java)
        return   obj
    }

    fun setUser(query:User, id :String) {
        val gson = Gson()
        val json = gson.toJson(query)
        prefs.edit().putString(id, json).apply()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }

    companion object {
        const val LOGIN: String = "login"
        const val USER_ID: String = "user_id"
        const val TOKEN: String = "token"
        const val FIREBASE_TOKEN: String = "firebase_token"
        const val LATTITUDE: String = "lat"
        const val LONGITUDE: String = "long"
        const val ADDRESS: String = "address"

    }

}