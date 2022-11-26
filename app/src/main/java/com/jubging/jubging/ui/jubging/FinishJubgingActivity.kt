package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.jubging.jubging.R
import com.jubging.jubging.databinding.ActivityFinishJubgingBinding
import com.jubging.jubging.ui.base.BaseActivity


class FinishJubgingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_jubging)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ShareActivity::class.java)
            this.intent.getStringExtra("URI")?.let { intent.putExtra("URI", it)}
            this.intent.getStringExtra("WEIGHT")?.let { intent.putExtra("WEIGHT", it)}

            startActivity(intent)
            finish()
        }, 1500)

    }
}