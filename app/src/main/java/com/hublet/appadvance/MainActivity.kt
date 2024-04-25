package com.hublet.appadvance

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hublet.appadvance.service.ExampleService
import com.hublet.appadvance.service.IExampleService

class MainActivity : AppCompatActivity() {

    private var mBound = false
    private var mService: IExampleService? = null

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = IExampleService.Stub.asInterface(service)
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
            mBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_start_service).setOnClickListener {
            if (mBound) {
                try {
                    mService?.showMessage("Hello from MainActivity!")
                    mService?.calculate(10, 20)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, ExampleService::class.java).also { intent ->
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
    }
}