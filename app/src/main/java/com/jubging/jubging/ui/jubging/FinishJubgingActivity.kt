package com.jubging.jubging.ui.jubging

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.provider.TedPermissionProvider
import com.jubging.jubging.R
import com.jubging.jubging.data.entities.Ids
import com.jubging.jubging.data.entities.Jubging
import com.jubging.jubging.data.remote.jubjubi.FinishJubgingView
import com.jubging.jubging.data.remote.jubjubi.JubjubiService
import com.jubging.jubging.databinding.ActivityFinishJubgingBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.main.HomeFragment
import com.jubging.jubging.ui.main.MainActivity
import com.mummoom.md.data.remote.auth.FinishJubgingResponse


class FinishJubgingActivity : AppCompatActivity(),FinishJubgingView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_jubging)
        Handler(Looper.getMainLooper()).postDelayed({
            finishJubging()
            val intent = Intent(this, ShareActivity::class.java)
            this.intent.getIntExtra("walk",0)?.let { intent.putExtra("walk", it)}
            this.intent.getStringExtra("time")?.let { intent.putExtra("time", it)}
            this.intent.getFloatExtra("distance",0.0f)?.let { intent.putExtra("distance", it)}
            this.intent.getFloatExtra("kcal",0.0f)?.let { intent.putExtra("kcal", it)}
            this.intent.getStringExtra("URI")?.let { intent.putExtra("URI", it)}
            this.intent.getStringExtra("WEIGHT")?.let { intent.putExtra("WEIGHT", it)}
            startActivity(intent)
            finish()
        }, 1500)
    }


    private fun finishJubging(){
        val tongs_id = intent.getIntExtra("tongs_id",0)
        val jubjubi_id =intent.getIntExtra("jubjubi_id",0)
        val weight = intent.getStringExtra("WEIGHT")!!.toFloat()
        val calorie = intent.getIntExtra("kcal",0)
        val distance = intent.getFloatExtra("distance",0.0f)
        val stepCnt = intent.getIntExtra("walk",0)
        val tongsReturn = intent.getBooleanExtra("tongs_return",true)
        val jubging = Jubging(tongs_id,jubjubi_id,weight,calorie,distance,stepCnt,tongsReturn)
        Log.d("줍깅 끝",jubging.toString())
        JubjubiService().finishJubging(this,jubging)
        MainActivity.jubgingCountM +=1
    }

    override fun onFinishJubgingLoading() {
    }

    override fun onFinishJubgingSuccess(finishJubgingResponse: FinishJubgingResponse) {
        Log.d("끝줍깅성공",finishJubgingResponse.toString())
    }

    override fun onFinishJubgingFailure(errorCode: Int, message: String) {
        when(errorCode){
            4040 ->{
                Toast.makeText(TedPermissionProvider.context, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4041 ->{
                Toast.makeText(TedPermissionProvider.context, "집게 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4042 ->{
                Toast.makeText(TedPermissionProvider.context, "줍줍이 정보가 없dmfRKdy.", Toast.LENGTH_SHORT).show()
                return
            }
            4043 ->{
                Toast.makeText(TedPermissionProvider.context, "진행 중인 줍깅 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }
}