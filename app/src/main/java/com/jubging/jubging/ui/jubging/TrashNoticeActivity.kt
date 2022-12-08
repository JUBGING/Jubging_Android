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
    override fun initAfterBinding() {
        binding.trashNoticeBtnTv.setOnClickListener {
            bluetoothThread.requestClose()
            val intent = Intent(this, FinishJubgingActivity::class.java)
            this.intent.getIntExtra("walk",0).let { intent.putExtra("walk", it)}
            this.intent.getStringExtra("time").let { intent.putExtra("time", it)}
            this.intent.getFloatExtra("distance",0.0f).let { intent.putExtra("distance", it)}
            this.intent.getIntExtra("kcal",0).let { intent.putExtra("kcal", it)}
            this.intent.getBooleanExtra("tongs_return",true).let { intent.putExtra("tongs_return", it)}
            this.intent.getStringExtra("URI")?.let { intent.putExtra("URI", it) }
            this.intent.getStringExtra("WEIGHT")?.let { intent.putExtra("WEIGHT", it) }
            this.intent.getIntExtra("jubjubi_id",0).let { intent.putExtra("jubjubi_id", it)}
            this.intent.getIntExtra("tongs_id",0).let { intent.putExtra("tongs_id", it)}
            Log.d("줍깅쓰레기확인",this.intent.getIntExtra("jubjubi_id",0).toString())
            bluetoothThread.cancel()
            startActivity(intent)
        }
    }


}