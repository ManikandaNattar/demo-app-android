package com.example.demoapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.content_listview_city.view.*

class CityListAdapter(private var context: Activity, private var citylist:List<CustomObject>)
: BaseAdapter() {
    private lateinit var city:String
    private var cityid by Delegates.notNull<Int>()

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any? {

        return position
    }

    override fun getCount(): Int {
        return citylist.size
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
        val inflater = context.layoutInflater
        var rowView=view
        if (rowView==null){
            rowView = inflater.inflate(R.layout.content_listview_city, null, true)
        }
        cityid= citylist[position].id!!
        rowView?.m_city_id?.text="CityID: $cityid"
        city = citylist[position].toString()
        rowView?.m_city?.text = "City: $city"
        return rowView
    }
}