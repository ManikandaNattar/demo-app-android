package com.example.demoapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_listview_item.view.*


class UserActivity : BaseActivity() ,NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var session: Session? = null
    private var name:String?=null
    lateinit var listView: ListView
    private var Tag:String="User Activity"
    private var userObjectOut: UserObjectOut?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_user)
            MyApplication()
            val userdata = findViewById<FloatingActionButton>(R.id.userfab)
            listView = findViewById(R.id.userlist)
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
            userObjectOut=ModelPreferencesManager.get<UserObjectOut>("KEY_USER")
            userObjectOut!!.userobj.reverse()
            val myListAdapter = userObjectOut?.let { MyListAdapter(this, it) }
            this.listView.adapter = userObjectOut?.let { MyListAdapter(this, it) }
            myListAdapter?.notifyDataSetChanged()
            listView.invalidateViews();
            listView.refreshDrawableState();
            listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->

                var username:String= listView[position].uname.text.toString().replace("UserName: ","")
                var usermobile:String=listView[position].umobile.text.toString().replace("Mobile: ","")
                var useremail:String=listView[position].uemail.text.toString().replace("Email: ","")
                var usercity:String=listView[position].city.text.toString().replace("City: ","")
                var usercityid: String =listView[position].cityid.text.toString().replace("CityID: ","")
                var usertype: String =listView[position].usertype.text.toString().replace("UserType: ","")
                val intent = Intent(this@UserActivity, EditUserActivity::class.java).apply {
                    putExtra("UserName",username)
                    putExtra("UserMobile",usermobile)
                    putExtra("UserEmail",useremail)
                    putExtra("UserCity",usercity)
                    putExtra("UserCityID",usercityid)
                    putExtra("UserMode","User")
                    putExtra("UserType",usertype)
                }
                startActivity(intent)
            }
 userdata?.setOnClickListener {
                val intent = Intent(this@UserActivity, AddUserActivity::class.java)
                startActivity(intent)
            }
        }
        catch (e:java.lang.Exception){}
}


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserActivity, MainActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                }
                R.id.nav_user -> {
                    Toast.makeText(this, "Already in User Page", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_city -> {
                    Toast.makeText(this, "City Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserActivity, CityActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                }
                R.id.nav_product -> {
                    Toast.makeText(this, "Product Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserActivity, ProductActivity::class.java).apply {
                        putExtra("UserName",name)
                    }
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserActivity, LoginActivity::class.java)
                    startActivity(intent)
                    session!!.getIsLoggedout()
                    //ModelPreferencesManager!!.dataClear()
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
        val intent = Intent(this@UserActivity, MainActivity::class.java).apply {

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
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(ConnectivityReceiver())
    }

    override fun onDestroy() {
        Log.d(Tag, "onDestroy:Called")
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(ConnectivityReceiver())
    }
}








