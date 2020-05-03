package com.example.demoapp


import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_listview_product.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL


class ProductActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener  {
    var dataList = ArrayList<HashMap<String, String>>()
    private var listView:ListView?=null
    private var productfab:FloatingActionButton?=null
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var session: Session? = null
    private var name:String?=null
    private var Tag:String="Product Activity"
    //private var delete:Button?=null
    final var jsonurl:String="https://aplus-nestjs-intro.herokuapp.com/products"
    val progressDialog=ProgressDialog()
private var productid:String?=null
    private var productname:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_product)
            listView = findViewById(R.id.product_list)
            productfab = findViewById(R.id.product_fab)
            //delete = findViewById(R.id.delete)
            session = Session(this)
            name = session!!.GetEmail()
            toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            drawerLayout = findViewById(R.id.drawer_layout)
            navView = findViewById(R.id.nav_view)
            val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            navView.setNavigationItemSelectedListener(this)
            productfab?.setOnClickListener() {
                val intent = Intent(this, AddProductActivity::class.java)
                startActivity(intent)
            }
            try {
                progressDialog.setProgressDialog(this@ProductActivity, "Loading...")
                val stringRequest: StringRequest = object : StringRequest(Method.GET, jsonurl,
                    Response.Listener { response ->
                         Toast.makeText(this, "Data Fetched Successfully", Toast.LENGTH_LONG).show()
                        try {
                            val usersArr = JSONArray(response)

                            for (i in 0 until usersArr.length()) {
                                val singleUser = usersArr.getJSONObject(i)
                                val map = HashMap<String, String>()
                                map["id"] = singleUser.getString("id")
                                map["title"] = singleUser.getString("title")
                                map["description"] = singleUser.getString("description")
                                map["price"] = singleUser.getString("price")
                                dataList.add(map)
                            }
                            Log.d("Fetched Data", response)
                            dataList = dataList.reversed() as ArrayList<HashMap<String, String>>
                            listView?.adapter = ProductListAdapter(this@ProductActivity, dataList)
                            progressDialog.hideProgressDialog()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                    }){

                }
                val requestQueue = Volley.newRequestQueue(this)
                requestQueue.add(stringRequest)
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
            listView?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

                productid =
                    listView!![position].product_id.text.toString().replace("ProductID: ", "")
                productname =
                    listView!![position].product_name.text.toString().replace("ProductName: ", "")
                var producrdesc: String =
                    listView!![position].product_desc.text.toString().replace("ProductDescription: ", "")
                var productprice: String =
                    listView!![position].product_price.text.toString().replace("ProductPrice: ", "")
                val intent = Intent(this@ProductActivity, EditProductActivity::class.java).apply {
                    putExtra("ProductID", productid)
                    putExtra("ProductName", productname)
                    putExtra("ProductDescription", producrdesc)
                    putExtra("ProductPrice", productprice)
                }
                startActivity(intent)
            }
//            delete?.setOnClickListener() {
//                try {
//                    AlertDialog.Builder(this)
//                        .setMessage("Do you want delete this product $productname")
//                        .setNegativeButton(android.R.string.no, null)
//                        .setPositiveButton(android.R.string.yes) { _, _ ->
//                            jsonurl += productid
//                            val stringRequest: StringRequest =
//                                object : StringRequest(Method.DELETE, jsonurl,
//                                    Response.Listener { response ->
//                                        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
//                                        try {
//                                            val jsonObject = JSONObject(response)
//                                            // productid=jsonObject.getString("id")
//                                            //Toast.makeText(this, productid, Toast.LENGTH_LONG).show()
//                                            Toast.makeText(
//                                                this,
//                                                "Product Deleted Successfully",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        } catch (e: JSONException) {
//                                            e.printStackTrace()
//                                        }
//                                    },
//                                    Response.ErrorListener { error ->
//                                        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG)
//                                            .show()
//                                    }) {
//
//                                }
//                            val requestQueue = Volley.newRequestQueue(this)
//                            requestQueue.add(stringRequest)
//                        }
//                        .create().show()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//
//
//            }
        }

        catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ProductActivity, MainActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                }
                R.id.nav_user -> {
                    val intent = Intent(this@ProductActivity, UserActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                    Toast.makeText(this, "User Page Clicked", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_city -> {
                    val intent = Intent(this@ProductActivity, CityActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                    Toast.makeText(this, "City Page Clicked", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_product -> {
                    Toast.makeText(this, "Already in Product Page", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ProductActivity, LoginActivity::class.java)
                    startActivity(intent)
                    session!!.getIsLoggedout()
                    //ModelPreferencesManager.dataClear()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
        catch (e: Exception)
        {
            return false
        }


    }
    override fun onBackPressed() {
        val intent = Intent(this@ProductActivity, MainActivity::class.java).apply {

            putExtra("UserName",name)
        }
        startActivity(intent)

    }
    override fun onStart() {
        Log.d(Tag, "onStart:Called")
        LocalBroadcastManager.getInstance(this).registerReceiver(ConnectivityReceiver(),IntentFilter())
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
