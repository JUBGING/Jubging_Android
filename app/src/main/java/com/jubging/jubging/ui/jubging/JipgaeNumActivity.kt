package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.widget.Toast
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.jubging.jubging.databinding.ActivityJipgaeNumBinding
import com.jubging.jubging.ui.base.BaseActivity

class JipgaeNumActivity: BaseActivity<ActivityJipgaeNumBinding>(ActivityJipgaeNumBinding::inflate) {
    override fun initAfterBinding() {
        binding.jipgaeNumConfirmTv.setOnClickListener {
            if(binding.jipgaeNumEt.text.isEmpty())
                Toast.makeText(this, "집게 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            else{
                val intent = Intent(this, JubgingDataActivity::class.java)
                startActivity(intent)
            }

        }
    }
}