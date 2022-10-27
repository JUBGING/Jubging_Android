package com.jubging.jubging.ui.signUp

import android.os.Handler
import android.os.Looper
import com.jubging.jubging.databinding.ActivitySignUpDoneBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.login.LoginActivity

class SignUpDoneActivity :BaseActivity<ActivitySignUpDoneBinding>(ActivitySignUpDoneBinding::inflate) {
    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivityWithClear(LoginActivity::class.java)
        }, 2000)
    }
}