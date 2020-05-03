package com.example.demoapp

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ModelPreferencesManager.with(this)
    }
}
data class UserObjectIn(val name:String,val mobile:String,val email:String,val cityid:Int,val city:String,val usertype:String)

data class UserObjectOut(var userobj:ArrayList<UserObjectIn>)

