package com.example.demoapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class EditUserActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var session: Session? = null
    private var name: EditText?=null
    private var name1:String?=null
    private var cityname:String?=null
    private var mobile: EditText?=null
    private var email: EditText?=null
    private var city: AutoCompleteTextView?=null
    private var usertype: AutoCompleteTextView?=null
    private var update: Button?=null
    private var cancel: Button?=null
    private var userObjectOut: UserObjectOut?=null
    private var cityid:Int=0
    private var ctyid:String?=null
    private var cid:Int=0
    private var arrayList= ArrayList<String>()
    private var add:Button?=null
    private var usertypes:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edit_user)
            MyApplication()
            session = Session(this)
            name1 = session!!.GetEmail()
            name = findViewById(R.id.edit_name)
            mobile = findViewById(R.id.edit_mobile)
            email = findViewById(R.id.edit_email)
            city = findViewById(R.id.edit_city)
            update = findViewById(R.id.btr_create)
            cancel = findViewById(R.id.btr_cancel)
            add = findViewById(R.id.btr_add)
            usertype=findViewById(R.id.edit_user_types)
            val bundle:Bundle?=intent.extras
            val username = bundle?.get("UserName")
            val usermobile =  bundle?.get("UserMobile")
            val useremail = bundle?.get("UserEmail")
            val usercity=  bundle?.get("UserCity")
            val usercityid=  bundle?.get("UserCityID")
            var cname:String=bundle?.get("Name").toString()
            var cemail:String=bundle?.get("Email").toString()
            var cmobile:String=bundle?.get("Mobile").toString()
            var ucid:String=usercityid.toString()
            var id=0
            cityname=bundle?.get("CityName").toString()
            ctyid= bundle?.get("CityID").toString()
            usertypes=bundle?.get("UserType").toString()
            if(ctyid!="null"){
            cityid=ctyid!!.toInt()}
            var UserMode:String=bundle?.get("UserMode").toString()
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
            val actv: AutoCompleteTextView? = city
            actv?.threshold = 1
            usercitymaster=ModelPreferencesManager.getCity<CustomObject>("KEY_CITY")
            actv?.setAdapter(arrayAdapter(usercitymaster))
            actv?.onItemClickListener = OnItemClickListener { parent, _, i, _ ->
                    cityid = (parent.getItemAtPosition(i) as CustomObject).id!!
            }
            val uactv: AutoCompleteTextView? = usertype
            uactv?.threshold = 1
            usertypemaster=ModelPreferencesManager.getCity<CustomObject>("KEY_USERTYPE")
            uactv?.setAdapter(arrayAdapter(usertypemaster))
            for (i in usercitymaster.indices){
                arrayList.add(usercitymaster[i].toString())
            }
            if (UserMode=="City"){
                name?.setText(cname)
                mobile?.setText(cmobile)
                email?.setText(cemail)
                city?.setText(cityname)
                usertype?.setText(usertypes)
            }
            if(UserMode=="User"){
                name?.setText(username.toString())
                mobile?.setText(usermobile.toString())
                email?.setText(useremail.toString())
                city?.setText(usercity.toString())
                usertype?.setText(usertypes)
            }
            actv?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    actv.showDropDown()
                    if(!arrayList.contains(city?.text.toString())){
                        city?.error = "No result found"
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                }
            })
            add!!.setOnClickListener(){
                val intent = Intent(this@EditUserActivity, AddCityActivity::class.java).apply {
                    putExtra("Name",name?.text)
                    putExtra("Email",email?.text)
                    putExtra("Mobile",mobile?.text)
                    putExtra("City",city?.text)
                    putExtra("UserType",usertype?.text)
                    putExtra("UserCity",usercity.toString())
                    putExtra("CityID",ucid)
                    putExtra("UserName",username.toString())
                    putExtra("UserEmail",useremail.toString())
                    putExtra("UserMobile",usermobile.toString())
                    putExtra("UserMode","EditUser")
                }
                startActivity(intent)
            }
                    update!!.setOnClickListener {
                        when {
                            TextUtils.isEmpty(name?.text) -> {
                                name?.error = "Enter a user name !"
                            }
                            TextUtils.isEmpty(email?.text) -> {
                                email?.error="Enter a user email !"
                            }
                            TextUtils.isEmpty(mobile?.text) -> {
                                mobile?.error="Enter a user mobile !"
                            }

                            TextUtils.isEmpty(city?.text) -> {
                                city?.error="Enter a city !"
                            }
                            !arrayList.contains(city?.text.toString())->{
                                city?.error = "Press a + icon to create a city"
                            }
                            else -> {
                                if(cityid==0){
                                    cityid=ucid.toInt()
                                }
                                cid= ucid.toInt()
                                var userObject = UserObjectIn(
                                    username.toString(),
                                    usermobile.toString(),
                                    useremail.toString(),cid,
                                    usercity.toString(), usertypes!!
                                )
                                userObjectOut =
                                    ModelPreferencesManager.get<UserObjectOut>("KEY_USER")
                                for (i in 0 until userObjectOut!!.userobj.size) {
                                    val arrayList = userObjectOut?.userobj?.contains(userObject)
                                    if (arrayList == true) {
                                        userObjectOut!!.userobj.remove(userObject)
                                    }
                                }
                                var userObjectIn = UserObjectIn(
                                    name?.text.toString(),
                                    mobile?.text.toString(),
                                    email?.text.toString(),cityid,
                                    city?.text.toString(),usertype?.text.toString()
                                )
                                userObjectOut?.userobj?.add(userObjectIn)
                                ModelPreferencesManager.put(userObjectOut!!, "KEY_USER")
                                var customObject: List<CustomObject> =
                                    ModelPreferencesManager.getCity<CustomObject>("KEY_USERTYPE")
                                id = customObject.last().id!!
                                id += 1
                                customObject += listOf<CustomObject>(CustomObject(id, usertype?.text.toString()))
                                ModelPreferencesManager.putCity(customObject, "KEY_USERTYPE")
                                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@EditUserActivity, UserActivity::class.java)
                                startActivity(intent)

                            }
                        }
                    }
                    cancel!!.setOnClickListener() {
                        val intent = Intent(this@EditUserActivity, UserActivity::class.java)
                        startActivity(intent)
                    }

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
                    val intent = Intent(this@EditUserActivity, MainActivity::class.java).apply {
                        putExtra("UserName", name1)
                    }
                    startActivity(intent)
                }
                R.id.nav_user -> {
                    val intent = Intent(this@EditUserActivity, UserActivity::class.java)
                    startActivity(intent)

                }
                R.id.nav_city -> {
                    Toast.makeText(this, "City Add Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditUserActivity, AddCityActivity::class.java).apply {
                        putExtra("UserName",name1)
                    }
                    startActivity(intent)
                }
                R.id.nav_product -> {
                    Toast.makeText(this, "Product Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditUserActivity, ProductActivity::class.java).apply {
                        putExtra("UserName",name1)
                    }
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditUserActivity, LoginActivity::class.java)
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
        val intent = Intent(this@EditUserActivity, UserActivity::class.java)
        startActivity(intent)
    }
}
