package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.jubging.jubging.data.entities.Ids
import com.jubging.jubging.data.remote.jubjubi.JubjubiService
import com.jubging.jubging.data.remote.jubjubi.StartJubgingView
import com.jubging.jubging.data.remote.userInfo.UserInfoService
import com.jubging.jubging.databinding.ActivityJipgaeNumBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.mummoom.md.data.remote.auth.StartJubgingResponse

class JipgaeNumActivity: BaseActivity<ActivityJipgaeNumBinding>(ActivityJipgaeNumBinding::inflate),StartJubgingView {
    override fun initAfterBinding() {
        binding.jipgaeNumConfirmTv.setOnClickListener {
            if(binding.jipgaeNumEt.text.isEmpty())
                Toast.makeText(this, "집게 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            else{
                startJubging()
                val intent = Intent(this, JubgingDataActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startJubging(){
        val tongsId = Integer.parseInt(binding.jipgaeNumEt.text.toString())
        val jubjubiId = intent.getIntExtra("jubjubi_id",0)//getIntExtra로 intent에서 가져오기
        val ids = Ids(tongsId,jubjubiId)
        Log.d("시작줍깅부르기",ids.toString())
        JubjubiService().startJubging(this,ids)
    }

    override fun onStartJubgingLoading() {
    }

    override fun onStartJubgingSuccess(startJubgingResponse: StartJubgingResponse) {
        Log.d("줍깅시작성공",startJubgingResponse.toString())
    }

    override fun onStartJubgingFailure(errorCode: Int, message: String) {
        when(errorCode){
            4040 ->{
                Toast.makeText(context, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4041 ->{
                Toast.makeText(context, "집게 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4042 ->{
                Toast.makeText(context, "줍줍이 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4005 ->{
                Toast.makeText(context, "이미 사용중인 집게 입니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4006 ->{
                Toast.makeText(context, "사용 할 수 없는 줍줍이 입니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }


}