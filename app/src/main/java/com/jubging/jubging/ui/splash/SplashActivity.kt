package com.jubging.jubging.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.jubging.jubging.R
import com.jubging.jubging.databinding.ActivitySplashBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.login.LoginActivity
import com.jubging.jubging.ui.main.MainActivity

//class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate){
//
//    override fun initAfterBinding() {
//        Handler(Looper.getMainLooper()).postDelayed({
//        }, 2000)
//    }
//}

//@SuppressLint("CustomSplashScreen")
//class SplashActivity : AppCompatActivity(){
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        Handler().postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            startActivity(intent)
//            finish()
//        },DURATION)
//    }
//    companion object{
//        private const val DURATION : Long = 2000
//    }
//
//}

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, 3000)

    }
}