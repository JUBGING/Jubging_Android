package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.jubging.jubging.ApplicationClass.Companion.bluetoothThread
import com.jubging.jubging.ApplicationClass.Companion.handlerd
import com.jubging.jubging.databinding.ActivityTrashNoticeBinding
import com.jubging.jubging.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_bluetooth.*

class TrashNoticeActivity:BaseActivity<ActivityTrashNoticeBinding>(ActivityTrashNoticeBinding::inflate) {
    private var weight: String = "0"
    override fun initAfterBinding() {
        binding.trashNoticeBtnTv.setOnClickListener {
            bluetoothThread.requestClose()
            val intent = Intent(this, FinishJubgingActivity::class.java)
            this.intent.getStringExtra("URI")?.let { intent.putExtra("URI", it) }
            this.intent.getStringExtra("WEIGHT")?.let { intent.putExtra("WEIGHT", it) }
            bluetoothThread.cancel()
            startActivity(intent)
        }
    }


}