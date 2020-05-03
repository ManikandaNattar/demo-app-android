package com.example.demoapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type


object ModelPreferencesManager {
    lateinit var preferences: SharedPreferences
    private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"

    fun with(application: Application) {
        preferences = application.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun put(userObjectOut: UserObjectOut, key: String) {
            val jsonString = GsonBuilder().create().toJson(userObjectOut)
            preferences.edit().putString(key, jsonString).apply()
    }
    fun putCity(customObject:List<CustomObject>, key: String) {
        val jsonString = GsonBuilder().create().toJson(customObject)
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): UserObjectOut {
        var userObjectOut: UserObjectOut
        return try {

            val gson = Gson()
            val json = preferences.getString(key, null)
            val type: Type = object : TypeToken<UserObjectOut?>() {}.type
            userObjectOut= gson.fromJson(json, type)
            if(userObjectOut==null){
                userObjectOut= UserObjectOut(userobj = ArrayList())
            }
            userObjectOut
        } catch (e:Exception){
            userObjectOut= UserObjectOut(userobj = ArrayList())
            userObjectOut
        }

    }
    inline fun <reified T> getCity(key: String): List<CustomObject> {
        var customObject: List<CustomObject>
        return try {

            val gson = Gson()
            val json = preferences.getString(key, null)
            val type: Type = object : TypeToken<List<CustomObject?>?>() {}.type
            customObject= gson.fromJson(json, type)
            if(customObject==null){
                customObject= listOf(CustomObject(0,""))
            }
            customObject
        } catch (e:Exception){
            customObject= listOf(CustomObject(i=0,s=""))
            customObject
        }

    }



    fun dataClear(){
        val edit= preferences.edit()
        edit.clear()
        edit.commit()
    }


}