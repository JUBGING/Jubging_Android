package com.jubging.jubging.ui.mypage

import android.content.Intent
import com.jubging.jubging.ApplicationClass
import com.jubging.jubging.ApplicationClass.Companion.Authorization
import com.jubging.jubging.databinding.FragmentMypageBinding
import com.jubging.jubging.ui.base.BaseFragment
import com.jubging.jubging.ui.login.LoginActivity

class MypageFragment:BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate){
    override fun initAfterBinding() {
        binding.mypageLogoutTv.setOnClickListener{
            logout()
        }
    }

    private fun logout() {
        val editor = ApplicationClass.mSharedPreferences.edit()
        editor.remove(Authorization)
        editor.apply()

        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

}