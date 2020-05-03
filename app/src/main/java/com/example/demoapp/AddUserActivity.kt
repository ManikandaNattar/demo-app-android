package com.example.demoapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class AddUserActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private var name: EditText?=null
    private var mobile: EditText?=null
    private var email: EditText?=null
    private var city: AutoCompleteTextView?=null
    private var usertype: AutoCompleteTextView?=null
    private var create:Button?=null
    private var clear:Button?=null
    private var add:Button?=null
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private var session: Session? = null
    private var name1:String?=null
    private var userObjectOut: UserObjectOut?=null
    private var cityid:Int=0
    private var flag:Int=0
    private var arrayList= ArrayList<String>()
    private var userarrayList= ArrayList<String>()
    private var useroperation:String?=null
    private var cname: String?=null
    private var cmobile: String?=null
    private var cemail: String?=null
    private var cityname:String?=null
    private var ctyid:String?=null
    private var usertypes:String?=null
    var i=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        MyApplication()
        name=findViewById(R.id.edit_user_name)
        mobile=findViewById(R.id.edit_user_mobile)
        email=findViewById(R.id.edit_user_email)
        city=findViewById(R.id.edit_user_city)
        create=findViewById(R.id.bt_create)
        clear=findViewById(R.id.bt_clear)
        add=findViewById(R.id.bt_add)
        usertype=findViewById(R.id.edit_user_type)
        session= Session(this)
        name1=session!!.GetEmail()
        val bundle: Bundle? =intent.extras
        var UserMode= bundle?.get("UserMode")
        cname=bundle?.get("Name").toString()
        cemail=bundle?.get("Email").toString()
        cmobile=bundle?.get("Mobile").toString()
        cityname=bundle?.get("City").toString()
        usertypes=bundle?.get("UserType").toString()
        useroperation=UserMode.toString()
        ctyid= bundle?.get("CityID").toString()
        if(ctyid!="null"){
            cityid=ctyid!!.toInt()}
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
        if(UserMode==null){
            UserMode="AddUser"
        }
        if(UserMode=="RegisterUser") {
            toolbar.navigationIcon = null
            supportActionBar?.setHomeButtonEnabled(false);
        }
        var actv: AutoCompleteTextView? = city
        actv?.threshold=1
        usercitymaster=ModelPreferencesManager.getCity<CustomObject>("KEY_CITY")
        actv?.setAdapter(arrayAdapter(usercitymaster))
        actv?.onItemClickListener = AdapterView.OnItemClickListener { parent, _, i, _ ->
            cityid = (parent.getItemAtPosition(i) as CustomObject).id!!

        }
        var uactv:AutoCompleteTextView?=usertype
        uactv?.threshold=1
        usertypemaster = ModelPreferencesManager.getCity<CustomObject>("KEY_USERTYPE")
        uactv?.setAdapter(arrayAdapter(usertypemaster))

        if(cname!="null"){
            name?.setText(cname)
            email?.setText(cemail)
            mobile?.setText(cmobile)
            city?.setText(cityname)
            usertype?.setText(usertypes)
        }

        for (i in usercitymaster.indices){
            arrayList.add(usercitymaster[i].toString())
            if(usercitymaster[i].toString()==city?.text.toString()){
                cityid= usercitymaster[i].id!!
            }
        }
        for (i in usertypemaster.indices){
            userarrayList.add(usertypemaster[i].toString())
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
//                actv?.showDropDown()
//                if(!arrayList.contains(city?.text.toString())){
//                    city?.error = "No result found"
//                }
            }
        })

              add!!.setOnClickListener(){
            val intent = Intent(this@AddUserActivity, AddCityActivity::class.java).apply {
                putExtra("Name",name?.text)
                putExtra("Email",email?.text)
                putExtra("Mobile",mobile?.text)
                putExtra("City",city?.text)
                putExtra("UserType",usertype?.text)
                putExtra("UserMode",UserMode.toString())
            }
            startActivity(intent)
        }
                create!!.setOnClickListener() {
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
                        city?.error="Enter a city!"
                    }
                    !arrayList.contains(city?.text.toString())->{
                        city?.error = "Press a + icon to create a city"
                    }
                    TextUtils.isEmpty(usertype?.text) -> {
                        usertype?.error="Enter a user type!"
                    }
                    else -> {
                        var userObjectIn=  UserObjectIn(
                            name?.text.toString(),
                            mobile?.text.toString(),
                            email?.text.toString(),cityid,
                            city?.text.toString(),
                            usertype?.text.toString()
                        )
                            userObjectOut=ModelPreferencesManager.get<UserObjectOut>("KEY_USER")
                            userObjectOut?.userobj?.add(userObjectIn)
                            ModelPreferencesManager.put(userObjectOut!!,"KEY_USER")
                        var customObject: List<CustomObject> =
                            ModelPreferencesManager.getCity<CustomObject>("KEY_USERTYPE")
                        i = customObject.last().id!!
                        i += 1
                        customObject += listOf<CustomObject>(CustomObject(i, usertype?.text.toString()))
                        ModelPreferencesManager.putCity(customObject, "KEY_USERTYPE")
                        if(UserMode=="RegisterUser") {
                            val intent = Intent(this@AddUserActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            val intent = Intent(this@AddUserActivity, UserActivity::class.java)
                            startActivity(intent)
                        }
                        Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            clear!!.setOnClickListener(){
                name?.setText("")
                mobile?.setText("")
                email?.setText("")
                city?.setText("")
                usertype?.setText("")
            }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddUserActivity, MainActivity::class.java).apply {
                        putExtra("UserName", name1)
                    }
                    startActivity(intent)
                }
                R.id.nav_user -> {
                    val intent = Intent(this@AddUserActivity, UserActivity::class.java)
                    startActivity(intent)

                }

                R.id.nav_city -> {
                    Toast.makeText(this, "City Add Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddUserActivity, AddCityActivity::class.java).apply {
                        putExtra("UserName",name1)
                    }
                    startActivity(intent)
                }
                R.id.nav_product -> {
                    Toast.makeText(this, "Product Page clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddUserActivity, ProductActivity::class.java).apply {
                        putExtra("UserName",name1)
                    }
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddUserActivity, LoginActivity::class.java)
                    startActivity(intent)
                    session!!.getIsLoggedout()
                    ModelPreferencesManager.dataClear()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        } catch (e: Exception) {
            return false
        }
    }


    override fun onBackPressed() {
        if(useroperation=="RegisterUser") {
            val intent = Intent(this@AddUserActivity, LoginActivity::class.java)
            startActivity(intent)
            toolbar.navigationIcon = null
            supportActionBar?.setHomeButtonEnabled(false);
        }
        else{
            val intent = Intent(this@AddUserActivity, UserActivity::class.java)
            startActivity(intent)
        }
    }


}
