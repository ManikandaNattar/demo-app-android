package com.example.demoapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class AddCityActivity : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener{
    private var city: EditText?=null
    private var create: Button?=null
    private var cancel: Button?=null
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var session: Session? = null
    private var name1:String?=null
    private var name: String?=null
    private var mobile: String?=null
    private var email: String?=null
    private var cityname:String?=null
    private var uname: String?=null
    private var umobile: String?=null
    private var uemail: String?=null
    private var ucityname:String?=null
    private var cityid:String?=null
    private var UserMode:String?=null
    private var usertypes:String?=null
    var i=0
    private var customObject:CustomObject?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)
        MyApplication()
        city = findViewById(R.id.add_city)
        create = findViewById(R.id.btrn_create)
        cancel = findViewById(R.id.btrn_cancel)
        session = Session(this)
        name1 = session!!.GetEmail()
        val bundle: Bundle? = intent.extras
        name=bundle?.get("Name").toString()
        email=bundle?.get("Email").toString()
        mobile=bundle?.get("Mobile").toString()
        cityname=bundle?.get("City").toString()
        uname=bundle?.get("UserName").toString()
        uemail=bundle?.get("UserEmail").toString()
        umobile=bundle?.get("UserMobile").toString()
        ucityname=bundle?.get("UserCity").toString()
        cityid=bundle?.get("CityID").toString()
        UserMode = bundle?.get("UserMode").toString()
        usertypes = bundle?.get("UserType").toString()
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
        if(cityname!="null"){
        city?.setText(cityname)}
        if(usertypes=="null"){
            usertypes=""
        }
        create!!.setOnClickListener() {
            when {
                TextUtils.isEmpty(city?.text) -> {
                    city?.error = "Enter a city name !"
                }
                else -> {
                    var customObject: List<CustomObject> =
                        ModelPreferencesManager.getCity<CustomObject>("KEY_CITY")
                    i = customObject.last().id!!
                    i += 1
                    if(customObject[0].id==0){
                        customObject = listOf<CustomObject>(CustomObject(i, city?.text.toString()))
                    }
                    else{
                        customObject += listOf<CustomObject>(CustomObject(i, city?.text.toString()))
                    }

                    ModelPreferencesManager.putCity(customObject, "KEY_CITY")
                    Toast.makeText(this, "City Created Successfully", Toast.LENGTH_SHORT).show()
                    if(UserMode=="AddUser") {
                        val intent =
                            Intent(this@AddCityActivity, AddUserActivity::class.java).apply {
                                putExtra("Name", name)
                                putExtra("Email", email)
                                putExtra("Mobile", mobile)
                                putExtra("City", cityname)
                                putExtra("CityID", i)
                                putExtra("UserType", usertypes)
                            }
                        startActivity(intent)
                    }
                    if(UserMode=="RegisterUser") {
                        val intent =
                            Intent(this@AddCityActivity, AddUserActivity::class.java).apply {
                                putExtra("Name", name)
                                putExtra("Email", email)
                                putExtra("Mobile", mobile)
                                putExtra("City", cityname)
                                putExtra("CityID", i)
                                putExtra("UserMode",UserMode.toString())
                            }
                        startActivity(intent)
                    }
                    if(UserMode=="EditUser"){
                        val intent =
                            Intent(this@AddCityActivity, EditUserActivity::class.java).apply {
                                putExtra("UserName", uname)
                                putExtra("UserEmail", uemail)
                                putExtra("UserMobile", umobile)
                                putExtra("UserCity", ucityname)
                                putExtra("UserCityID", cityid)
                                putExtra("CityName", cityname)
                                putExtra("Name", name)
                                putExtra("Email", email)
                                putExtra("Mobile", mobile)
                                putExtra("CityID", i)
                                putExtra("UserMode", "City")
                                putExtra("UserType", usertypes)
                            }
                        startActivity(intent)
                    }
                    if(UserMode=="null"){
                        val intent = Intent(this@AddCityActivity, CityActivity::class.java).apply {
                            putExtra("UserName", name1)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
        cancel!!.setOnClickListener(){
            if(UserMode=="AddUser") {
                val intent =
                    Intent(this@AddCityActivity, AddUserActivity::class.java).apply {
                        putExtra("Name", name)
                        putExtra("Email", email)
                        putExtra("Mobile", mobile)
                        putExtra("City", cityname)
                        putExtra("UserMode", UserMode.toString())
                        putExtra("UserType", usertypes)
                    }
                startActivity(intent)
            }
            if(UserMode=="RegisterUser") {
                val intent =
                    Intent(this@AddCityActivity, AddUserActivity::class.java).apply {
                        putExtra("Name", name)
                        putExtra("Email", email)
                        putExtra("Mobile", mobile)
                        putExtra("City", cityname)
                        putExtra("CityID", i)
                        putExtra("UserMode",UserMode.toString())
                        putExtra("UserType", usertypes)
                    }
                startActivity(intent)
            }
            if(UserMode=="EditUser"){
                val intent =
                    Intent(this@AddCityActivity, EditUserActivity::class.java).apply {
                        putExtra("UserName", uname)
                        putExtra("UserEmail", uemail)
                        putExtra("UserMobile", umobile)
                        putExtra("UserCity", ucityname)
                        putExtra("UserCityID", cityid)
                        putExtra("CityName", cityname)
                        putExtra("Name", name)
                        putExtra("Email", email)
                        putExtra("Mobile", mobile)
                        putExtra("CityID", i)
                        putExtra("UserMode", "City")
                        putExtra("UserType", usertypes)
                    }
                startActivity(intent)
            }
            if(UserMode=="null"){
                val intent = Intent(this@AddCityActivity, CityActivity::class.java).apply {
                    putExtra("UserName", name1)
                }
                startActivity(intent)
            }
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddCityActivity, MainActivity::class.java).apply {
                        putExtra("UserName", name1)
                    }
                    startActivity(intent)
                }
                R.id.nav_user -> {
                    Toast.makeText(this, "User Page Clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddCityActivity, UserActivity::class.java)
                    startActivity(intent)

                }
                R.id.nav_city -> {
                    Toast.makeText(this, "Already in City Add Page", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddCityActivity, LoginActivity::class.java)
                    startActivity(intent)
                    session!!.getIsLoggedout()
                    //ModelPreferencesManager!!.dataClear()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        } catch (e: Exception) {
            return false
        }
    }


    override fun onBackPressed() {
        if(UserMode=="AddUser") {
            val intent = Intent(this@AddCityActivity, AddUserActivity::class.java)
            startActivity(intent)
        }
        if(UserMode=="EditUser") {
            val intent = Intent(this@AddCityActivity, EditUserActivity::class.java)
            startActivity(intent)
        }
        if(UserMode=="RegisterUser") {
            val intent =
                Intent(this@AddCityActivity, AddUserActivity::class.java)
            startActivity(intent)
        }
        if(UserMode=="null"){
            val intent = Intent(this@AddCityActivity, CityActivity::class.java).apply {
                putExtra("UserName", name1)
            }
            startActivity(intent)
        }
    }
}
