package com.example.demoapp

import android.content.Context
import android.widget.ArrayAdapter

class CustomObject(i: Int, s: String) {
    var id:Int?=null
    private var name:String?=null

    init{
        this.id=i
        this.name=s
    }

    override fun toString(): String {
        return name.toString()
    }
}

var usercitymaster = ModelPreferencesManager.getCity<CustomObject>("KEY_CITY")

var usertypemaster = ModelPreferencesManager.getCity<CustomObject>("KEY_USERTYPE")

fun Context.arrayAdapter(objects: List<CustomObject>): ArrayAdapter<CustomObject> {
    return ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, objects)
}