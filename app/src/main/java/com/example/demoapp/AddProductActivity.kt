package com.example.demoapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class AddProductActivity : BaseActivity() {
    private var toolbar:Toolbar?=null
    private var pname:EditText?=null
    private var pdesc:EditText?=null
    private var pprice:EditText?=null
    private var create:Button?=null
    private var clear:Button?=null
    final val jsonurl:String="https://aplus-nestjs-intro.herokuapp.com/products/"
    private var Tag:String="Add Product Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        toolbar=findViewById(R.id.toolbar)
        pname=findViewById(R.id.input_product_names)
        pdesc=findViewById(R.id.input_product_description)
        pprice=findViewById(R.id.input_product_prices)
        create=findViewById(R.id.prt_create)
        clear=findViewById(R.id.prt_clear)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar?.setNavigationOnClickListener {
            val intent = Intent(this@AddProductActivity, ProductActivity::class.java)
            startActivity(intent)
        }
        create!!.setOnClickListener() {
            when {
                TextUtils.isEmpty(pname?.text) -> {
                    pname?.error = "Enter a product name !"
                }

                TextUtils.isEmpty(pdesc?.text) -> {
                    pdesc?.error="Enter a product description !"
                }
                TextUtils.isEmpty(pprice?.text) -> {
                    pprice?.error="Enter a product price !"
                }
                else -> {
                    try {
                        val obj = JSONObject()
                        obj.put("title", pname?.text.toString())
                        obj.put("desc", pdesc?.text.toString())
                        obj.put("price", pprice?.text.toString())
                        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest( Method.POST, jsonurl,obj,
                            Response.Listener { response ->
                                Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                                try {
                                   // val jsonObject = JSONObject(response)
                                    //Parse your api responce here
                                    /*val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)*/
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            },
                            Response.ErrorListener { error ->
                                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                            }) {}
//                            {
//                            override fun getParams(): Map<String, String> {
//                                val params: MutableMap<String, String> = HashMap()
//                                //Change with your post params
//                                params["title"] = pname?.text.toString()
//                                params["desc"] = pdesc?.text.toString()
//                                params["price"] = pprice?.text.toString()
//                                return params
//                            }
//                        }
                        val requestQueue = Volley.newRequestQueue(this)
                        requestQueue.add(jsonObjectRequest)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                        val intent = Intent(this@AddProductActivity, ProductActivity::class.java)
                        startActivity(intent)
                    Toast.makeText(this, "Product Created Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
        clear!!.setOnClickListener(){
            pname?.setText("")
            pdesc?.setText("")
            pprice?.setText("")
        }


    }
    override fun onBackPressed() {
        val intent = Intent(this@AddProductActivity, ProductActivity::class.java)
        startActivity(intent)

    }
    override fun onStart() {
        Log.d(Tag, "onStart:Called")
        LocalBroadcastManager.getInstance(this).registerReceiver(ConnectivityReceiver(),
            IntentFilter()
        )
        super.onStart()
    }

    override fun onRestart() {
        Log.d(Tag, "onRestart:Called")

        super.onRestart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(Tag, "onRestoreInstanceState:Called")

        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onResume() {
        Log.d(Tag, "onResume:Called")

        super.onResume()
    }

    override fun onPause() {
        Log.d(Tag, "onPause:Called")

        super.onPause()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(Tag, "onSaveInstanceState:Called")

        super.onSaveInstanceState(outState)

    }

    override fun onStop() {
        Log.d(Tag, "onStop:Called")
        LocalBroadcastManager.getInstance(this).unregisterReceiver(ConnectivityReceiver())
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(Tag, "onDestroy:Called")
        LocalBroadcastManager.getInstance(this).unregisterReceiver(ConnectivityReceiver())
        super.onDestroy()
    }

}
