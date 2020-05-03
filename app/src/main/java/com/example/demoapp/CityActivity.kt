package com.example.demoapp

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_edit_city.view.*
import kotlinx.android.synthetic.main.content_listview_city.view.*

class CityActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var session: Session? = null
    private var name:String?=null
    lateinit var listView: ListView
    private var Tag:String="City Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        MyApplication()
        val cityfab = findViewById<FloatingActionButton>(R.id.cityfab)
        listView = findViewById(R.id.citylist)
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
        var customObject: List<CustomObject> =ModelPreferencesManager.getCity<CustomObject>("KEY_CITY")
        customObject= customObject.reversed()
        val cityListAdapter = CityListAdapter(this, customObject)
        this.listView.adapter = CityListAdapter(this, customObject)
        cityListAdapter.notifyDataSetChanged()
        listView.invalidateViews();
        listView.refreshDrawableState();
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            val city: String = listView[position].m_city.text.toString().replace("City: ", "")
            val cityid: String =
                listView[position].m_city_id.text.toString().replace("CityID: ", "")
                    val titleView = TextView(this)
        titleView.text = "Edit City"
        titleView.gravity = Gravity.CENTER
        titleView.setPadding(20, 20, 20, 20)
        titleView.textSize = 20f
        titleView.typeface = Typeface.DEFAULT_BOLD;
        titleView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        titleView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.content_edit_city, null)
        val mBuilder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
            .setView(mDialogView)
            .setCustomTitle(titleView)
        val mAlertDialog = mBuilder.show()
            mDialogView.edit_mcity.setText(city)
    mDialogView.bton_update!!.setOnClickListener {

                when {
                    TextUtils.isEmpty(city) -> {
                        Toast.makeText(this, "Enter a city", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        var customObject: List<CustomObject> =
                            ModelPreferencesManager.getCity<CustomObject>("KEY_CITY")
                        val id:Int=cityid.toInt()
                        val result=customObject.filter{ it.id?.equals(id)!! }
                        customObject-=result
                        customObject += listOf<CustomObject>(CustomObject(cityid.toInt(), mDialogView.edit_mcity.text.toString()))
                        ModelPreferencesManager.putCity(customObject, "KEY_CITY")
                        Toast.makeText(this, "City Updated Successfully", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                        customObject=ModelPreferencesManager.getCity<CustomObject>("KEY_CITY")
                        customObject=customObject.reversed()
                        this.listView.adapter = customObject?.let { CityListAdapter(this, it) }
                        cityListAdapter.notifyDataSetChanged()
                        listView.invalidateViews();
                        listView.refreshDrawableState();

                                           }
                }
            }
            mDialogView.bton_cancel!!.setOnClickListener() {
                mAlertDialog.dismiss()
            }
        }
        cityfab?.setOnClickListener(){
            val intent=Intent(this,AddCityActivity::class.java)
            startActivity(intent)
        }


}

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CityActivity, MainActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                }
                R.id.nav_user -> {
                    val intent = Intent(this@CityActivity, UserActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                    Toast.makeText(this, "User Page Clicked", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_city -> {
                    Toast.makeText(this, "Already in City Page", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_product -> {
                    Toast.makeText(this, "Product Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CityActivity, ProductActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CityActivity, LoginActivity::class.java)
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
        val intent = Intent(this@CityActivity, MainActivity::class.java).apply {

            putExtra("UserName",name)
        }
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
