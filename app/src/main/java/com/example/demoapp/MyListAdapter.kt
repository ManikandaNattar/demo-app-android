package com.example.demoapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.content_listview_item.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class MyListAdapter(private var context: Activity, private var userlist:UserObjectOut)
    : BaseAdapter() {
    private lateinit var name: String
    private lateinit var email:String
    private lateinit var mobile:String
    private lateinit var city:String
    private var usertype:String?=null
    private var cityid by Delegates.notNull<Int>()

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any? {

        return position
    }

    override fun getCount(): Int {
        return userlist.userobj.size
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
    val inflater = context.layoutInflater
    var rowView=view
    if (rowView==null){
     rowView = inflater.inflate(R.layout.content_listview_item, null, true)
    }

    name = userlist.userobj[position].name
        rowView?.uname?.text = "UserName: $name"
        mobile = userlist.userobj[position].mobile
        rowView?.umobile?.text = "Mobile: $mobile"
        email = userlist.userobj[position].email
        rowView?.uemail?.text = "Email: $email"
        cityid = userlist.userobj[position].cityid
        rowView?.cityid?.text = "CityID: $cityid"
        city = userlist.userobj[position].city
        rowView?.city?.text = "City: $city"
        usertype = userlist.userobj[position].usertype
        rowView?.usertype?.text = "UserType: $usertype"

                return rowView
    }
}





