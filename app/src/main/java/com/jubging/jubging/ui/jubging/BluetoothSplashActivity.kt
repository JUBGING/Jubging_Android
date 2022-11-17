package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.jubging.jubging.R
import com.jubging.jubging.ui.main.MainActivity


class BluetoothSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_splash)
        LoadingStart()
    }

    private fun LoadingStart(){
        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(applicationContext, BluetoothActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}