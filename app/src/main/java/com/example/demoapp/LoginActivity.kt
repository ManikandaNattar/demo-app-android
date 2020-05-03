package com.example.demoapp

import android.content.Intent
import android.content.IntentFilter
import android.nfc.Tag
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class LoginActivity : BaseActivity() {
    private var etuseremail: EditText?=null
    private var etpassword: EditText?=null
    private var btnregister: Button?= null
    private var btnsubmit: Button?= null
    private var session: Session? = null
    lateinit var email:String
    lateinit var city:String
    private var userObjectOut: UserObjectOut?=null
private var Tag:String="Login Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            session = Session(this)
            etuseremail = findViewById(R.id.etuseremail)
            etpassword = findViewById(R.id.etpassword)
            btnregister = findViewById(R.id.btn_register)
            btnsubmit = findViewById(R.id.btn_submit)
            btnregister?.setOnClickListener {
                registerUser()
            }
            btnsubmit?.setOnClickListener {
                btnSubmit()
            }
            if (session!!.getIsLogin()) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("UserName", session!!.GetEmail())
                startActivity(intent)
                this.finish()
            }

        }
        catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }


    private fun btnSubmit() {
        try {
            val username = etuseremail!!.text
            val password = etpassword!!.text
            var result:Boolean?=false
            userObjectOut=ModelPreferencesManager.get<UserObjectOut>("KEY_USER")
            for (i in 0 until userObjectOut!!.userobj.size){
              if(userObjectOut!!.userobj[i].email.contains(username)){
                  result=true
                }
            }
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter UserEmail/Password", Toast.LENGTH_LONG).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etuseremail!!.text).matches()) {
                Toast.makeText(this, "Enter Valid UserEmail", Toast.LENGTH_LONG).show()
            }
            else if(result==false){
                Toast.makeText(this, "User Not Registered", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "User Logged In", Toast.LENGTH_LONG).show()
                session!!.PutEmail(username.toString())
                session!!.PutPassword(password.toString())
                session!!.putIsLogin(true)
                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    putExtra("UserName", username)
                }
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun registerUser() {
        val intent = Intent(this@LoginActivity, AddUserActivity::class.java).apply {
            putExtra("UserMode", "RegisterUser")
        }
        startActivity(intent)
    }
    override fun onBackPressed() = finishAffinity()

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


