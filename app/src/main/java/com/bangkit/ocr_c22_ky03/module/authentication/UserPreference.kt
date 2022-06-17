package com.bangkit.ocr_c22_ky03.module.authentication

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {

    val preference: SharedPreferences = context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)

    fun setUserLogin(email: String, token: String, login: Int) {
        preference.edit()
            .putString(EMAIL, email)
            .putInt(ID, login)
            .putString(TOKEN, token)
            .apply()
    }

    fun deleteUserLogin() {
        preference.edit()
            .putString(EMAIL, "")
            .putInt(ID, 0)
            .putString(TOKEN, "")
            .putString(PATH, "")
            .apply()
    }

    companion object {
        const val LOGIN_SESSION = "login_session"
        const val EMAIL = "email"
        const val ID = "id"
        const val TOKEN = "token"
        const val PATH = "path"
    }
}