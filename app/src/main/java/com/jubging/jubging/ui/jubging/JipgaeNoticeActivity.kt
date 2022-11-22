package com.jubging.jubging.ui.jubging

import android.content.Intent
import com.jubging.jubging.databinding.ActivityJipgaeNoticeBinding
import com.jubging.jubging.ui.base.BaseActivity

class JipgaeNoticeActivity :BaseActivity<ActivityJipgaeNoticeBinding>(ActivityJipgaeNoticeBinding::inflate) {
    override fun initAfterBinding() {

        binding.jipgaeConfirmTv.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}