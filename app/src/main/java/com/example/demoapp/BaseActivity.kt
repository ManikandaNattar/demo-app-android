package com.example.demoapp

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private var mSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }


    @SuppressLint("WrongConstant")
    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            val messageToUser = "Problem in network connection.Please try again"
            AlertDialog.Builder(this)
                .setMessage(messageToUser)
                .setNegativeButton(android.R.string.ok,null)
                .create().show()
//            mSnackBar = Snackbar.make(findViewById( R.id.root_Layout), messageToUser, Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
//            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
//            mSnackBar?.show()
        }
//        else{
//            mSnackBar?.dismiss()
//        }
    }

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }


    /**
     * Callback will be called when there is change
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}