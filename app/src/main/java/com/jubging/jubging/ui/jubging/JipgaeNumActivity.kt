package com.jubging.jubging.ui.jubging

import android.content.Intent
import com.jubging.jubging.databinding.ActivityJipgaeNumBinding
import com.jubging.jubging.ui.base.BaseActivity

class JipgaeNumActivity: BaseActivity<ActivityJipgaeNumBinding>(ActivityJipgaeNumBinding::inflate) {
    override fun initAfterBinding() {
        binding.jipgaeNumConfirmTv.setOnClickListener {
            val intent = Intent(this, JubgingDataActivity::class.java)
            startActivity(intent)
        }
    }
}