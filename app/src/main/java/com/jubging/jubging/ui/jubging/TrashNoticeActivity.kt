package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.jubging.jubging.databinding.ActivityTrashNoticeBinding
import com.jubging.jubging.ui.base.BaseActivity

class TrashNoticeActivity:BaseActivity<ActivityTrashNoticeBinding>(ActivityTrashNoticeBinding::inflate) {
    override fun initAfterBinding() {
        binding.trashNoticeBtnTv.setOnClickListener {
            val intent = Intent(this, FinishJubgingActivity::class.java)
            this.intent.getStringExtra("URI")?.let { intent.putExtra("URI", it)}
            startActivity(intent)
        }
    }


}