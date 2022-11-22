package com.jubging.jubging.ui.jubging

import android.content.Intent
import com.jubging.jubging.databinding.ActivityTrashNoticeBinding
import com.jubging.jubging.ui.base.BaseActivity

class TrashNoticeActivity:BaseActivity<ActivityTrashNoticeBinding>(ActivityTrashNoticeBinding::inflate) {
    override fun initAfterBinding() {
        binding.trashNoticeBtnTv.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)
        }

    }
}