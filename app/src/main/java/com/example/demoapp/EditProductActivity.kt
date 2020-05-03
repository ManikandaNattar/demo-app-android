package com.example.demoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class EditProductActivity : AppCompatActivity() {
    private var toolbar: Toolbar?=null
    private var pname: EditText?=null
    private var pdesc: EditText?=null
    private var pprice: EditText?=null
    private var update: Button?=null
    private var cancel: Button?=null
    private var productname:String?=null
    private var productid:String?=null
    private var productdesc:String?=null
    private var productprice:String?=null

    final var jsonurl:String="https://aplus-nestjs-intro.herokuapp.com/products/"
    private var Tag:String="Edit Product Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)
        val bundle:Bundle?=intent.extras
        toolbar=findViewById(R.id.toolbar)
        pname=findViewById(R.id.edit_product_names)
        pdesc=findViewById(R.id.edit_product_description)
        pprice=findViewById(R.id.edit_product_prices)
        update=findViewById(R.id.prod_update)
        cancel=findViewById(R.id.prod_cancel)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar?.setNavigationOnClickListener {
            val intent = Intent(this@EditProductActivity, ProductActivity::class.java)
            startActivity(intent)
        }
        productid=bundle?.getString("ProductID")
        productname=bundle?.getString("ProductName")
        productdesc=bundle?.getString("ProductDescription")
        productprice=bundle?.getString("ProductPrice")
        pname?.setText(productname)
        pdesc?.setText(productdesc)
        pprice?.setText(productprice)
        update!!.setOnClickListener {
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
                        jsonurl += productid
                        val stringRequest: StringRequest = object : StringRequest( Method.PATCH, jsonurl,
                            Response.Listener { response ->
                                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                                try {
                                    val jsonObject = JSONObject(response)
                                   // productid=jsonObject.getString("id")
                                    //Toast.makeText(this, productid, Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@EditProductActivity, ProductActivity::class.java)
                                    startActivity(intent)
                                    Toast.makeText(this, "Product Updated Successfully", Toast.LENGTH_SHORT).show()
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            },
                            Response.ErrorListener { error ->
                                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                            }) {
                            override fun getParams(): Map<String, String> {
                                val params: MutableMap<String, String> = HashMap()
                                //Change with your post params
                                params["title"] = pname?.text.toString()
                                params["description"] = pdesc?.text.toString()
                                params["price"] = pprice?.text.toString()
                                return params
                            }
                        }
                        val requestQueue = Volley.newRequestQueue(this)
                        requestQueue.add(stringRequest)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        cancel!!.setOnClickListener() {
            val intent = Intent(this@EditProductActivity, ProductActivity::class.java)
            startActivity(intent)
        }
    }
}
