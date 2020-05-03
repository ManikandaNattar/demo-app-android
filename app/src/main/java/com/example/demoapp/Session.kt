package com.example.demoapp

import android.content.Context
import android.content.SharedPreferences
private const val INTRO = "intro"
private const val Email = "Email"
private const val Password = "Password"

class Session (private val context: Context){
    private val appPrefs: SharedPreferences = (context.getSharedPreferences(
        "shared",
        Context.MODE_PRIVATE
    ))!!

    fun putIsLogin(loginorout: Boolean) {
        val edit = appPrefs.edit()
        edit.putBoolean(INTRO, loginorout)
        edit.commit()
    }

    fun getIsLogin(): Boolean {
        return appPrefs.getBoolean(INTRO, false)
    }

    fun PutEmail(loginorout: String) {
        val edit = appPrefs.edit()
        edit.putString(Email, loginorout)
        edit.commit()
    }

    fun GetEmail(): String? {
        return appPrefs.getString(Email, "")
    }

    fun PutPassword(loginorout: String) {
        val edit = appPrefs.edit()
        edit.putString(Password, loginorout)
        edit.commit()
    }

    fun GetPassword(): String? {
        return appPrefs.getString(Password, "")
    }
    fun getIsLoggedout(){
        val edit=appPrefs.edit()
        edit.clear()
        edit.commit()
    }

}