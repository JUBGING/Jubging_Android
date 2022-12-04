package com.jubging.jubging.ui.mypage

import com.jubging.jubging.databinding.ActivityChangePwdBinding
import com.jubging.jubging.ui.base.BaseActivity

class ChangePwdActivity: BaseActivity<ActivityChangePwdBinding>(ActivityChangePwdBinding::inflate) {
    override fun initAfterBinding() {
        binding.changePwdBackIv.setOnClickListener {
            finish()
        }

    }
}