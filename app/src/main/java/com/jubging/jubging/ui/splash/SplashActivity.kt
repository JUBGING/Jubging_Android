package com.jubging.jubging.ui.splash

import android.os.Looper
import android.os.Handler
import com.jubging.jubging.databinding.ActivitySplashBinding
import com.jubging.jubging.ui.base.BaseActivity


class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate){

    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({

        },2000)
    }
}