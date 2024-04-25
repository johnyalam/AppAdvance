package com.hublet.appadvance.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ExampleService : Service() {
    private val binder = object : IExampleService.Stub() {
        override fun showMessage(message: String?) {
            Log.d("ExampleService", "Message: $message")
        }

        override fun calculate(x: Int, y: Int): Int {
            Log.d("ExampleService", "Message: ${x + y}")
            return x+y
        }
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }
}