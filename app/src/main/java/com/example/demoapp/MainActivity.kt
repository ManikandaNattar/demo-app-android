package com.example.demoapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : NavigationView.OnNavigationItemSelectedListener ,BaseActivity() {
    private var toolbar: Toolbar?=null
    private var drawerLayout: DrawerLayout?=null
    private var navView: NavigationView?=null
    private var session: Session? = null
    private var useremail:TextView?=null
    private var logindate:TextView?=null
    private var Tag:String="Main Activity"


    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            useremail=findViewById(R.id.useremail)
            logindate=findViewById(R.id.logindate)
            session = Session(this)
            toolbar = findViewById(R.id.toolbar)

            setSupportActionBar(toolbar)
            drawerLayout = findViewById(R.id.drawer_layout)
            navView = findViewById(R.id.nav_view)
            val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0
            )
            drawerLayout?.addDrawerListener(toggle)
            toggle.syncState()
            navView?.setNavigationItemSelectedListener(this)
            val bundle: Bundle? = intent.extras
            val username = bundle?.get("UserName")
            useremail?.text = username.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
                var answer: String = current.format(formatter)
                logindate?.text = answer

            } else {
                var date = Date()
                val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                val answer: String = formatter.format(date)
                logindate?.text = answer
            }
        }
        catch (e:Exception){e.printStackTrace()}
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Toast.makeText(this, "Home Page clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_user -> {
                Toast.makeText(this, "User Page clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, UserActivity::class.java).apply {
                    putExtra("UserName",useremail?.text)
                }
                startActivity(intent)
            }
            R.id.nav_city -> {
                Toast.makeText(this, "City Page clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, CityActivity::class.java).apply {
                    putExtra("UserName",useremail?.text)
                }
                startActivity(intent)
            }
            R.id.nav_product -> {
                Toast.makeText(this, "Product Page clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, ProductActivity::class.java).apply {
                    putExtra("UserName",useremail?.text)
                }
                startActivity(intent)
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                session!!.getIsLoggedout()
                //ModelPreferencesManager.dataClear()
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() = AlertDialog.Builder(this)
        .setMessage("Do you want to exit?")
        .setNegativeButton(android.R.string.no, null)
        .setPositiveButton(android.R.string.yes) { _, _ -> finishAffinity() }
        .create().show()

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
