package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.util.Log
import com.jubging.jubging.databinding.ActivityJipgaeNoticeBinding
import com.jubging.jubging.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_bluetooth.*

class JipgaeNoticeActivity :BaseActivity<ActivityJipgaeNoticeBinding>(ActivityJipgaeNoticeBinding::inflate) {
    override fun initAfterBinding() {

        binding.jipgaeConfirmTv.setOnClickListener {
            val tongs_return = true
            val intent = Intent(this, CameraActivity::class.java)
            this.intent.getIntExtra("walk",0).let { intent.putExtra("walk", it)}
            this.intent.getStringExtra("time").let { intent.putExtra("time", it)}
            this.intent.getFloatExtra("distance",0.0f).let { intent.putExtra("distance", it)}
            this.intent.getFloatExtra("kcal",0.0f).let { intent.putExtra("kcal", it)}
            this.intent.getIntExtra("jubjubi_id",0).let { intent.putExtra("jubjubi_id", it)}
            this.intent.getIntExtra("tongs_id",0).let { intent.putExtra("tongs_id", it)}
            intent.putExtra("tongs_return ", tongs_return)

            Log.d("줍깅집게반납this",this.intent.getIntExtra("jubjubi_id",0).toString())
            Log.d("줍깅집게반납",intent.getIntExtra("jubjubi_id",0).toString())
            startActivity(intent)
        }
    }
}