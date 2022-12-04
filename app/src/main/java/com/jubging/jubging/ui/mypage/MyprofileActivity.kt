package com.jubging.jubging.ui.mypage

import com.jubging.jubging.databinding.ActivityMyprofileBinding
import com.jubging.jubging.ui.base.BaseActivity

class MyprofileActivity:BaseActivity<ActivityMyprofileBinding>(ActivityMyprofileBinding::inflate) {
    override fun initAfterBinding() {
        binding.myprofileBackIv.setOnClickListener {
            finish()
        }

    }
}