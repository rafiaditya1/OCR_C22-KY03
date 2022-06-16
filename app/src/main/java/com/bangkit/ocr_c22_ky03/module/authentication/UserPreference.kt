package com.bangkit.ocr_c22_ky03.module.authentication

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {

    val preference: SharedPreferences = context.getSharedPreferences("login_session", Context.MODE_PRIVATE)

    fun setUserLogin(email: String, token: String) {
        preference.edit()
            .putString("email", email)
            .putString("token", token)
            .apply()
    }

    fun deleteUserLogin() {
        preference.edit()
            .putString("email", "")
            .putString("token", "")
            .apply()
    }

//    fun getUserLogin() {
//        val email = preference.getString(EMAIL, "")
//        val token = preference.getString(TOKEN, "")
//    }

    companion object {
        const val LOGIN_SESSION = "login_session"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val TOKEN = "token"
    }
}