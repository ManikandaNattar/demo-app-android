package com.example.demoapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.content_listview_product.view.*

class ProductListAdapter(private var context: Activity, private var datalist:ArrayList<HashMap<String,String>>):BaseAdapter() {
    private var pid:String?=null
    private var pname:String?=null
    private var pdesc:String?=null
    private var pprice:String?=null
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any? {
        return position
    }

    override fun getCount(): Int {
        return datalist.size
    }
    override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
        val inflater = context.layoutInflater
        var rowView=view
        if (rowView==null){
            rowView = inflater.inflate(R.layout.content_listview_product, null, true)
        }
        pid= datalist[position]["id"]!!
        rowView?.product_id?.text="ProductID: $pid"
        pname = datalist[position]["title"]
        rowView?.product_name?.text = "ProductName: $pname"
        pdesc = datalist[position]["description"]
        rowView?.product_desc?.text = "ProductDescription: $pdesc"
        pprice = datalist[position]["price"]
        rowView?.product_price?.text = "ProductPrice: $pprice"
        return rowView
    }
}