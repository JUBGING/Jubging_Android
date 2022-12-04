package com.jubging.jubging.ui.mypage

import android.util.Log
import android.widget.Toast
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.jubging.jubging.data.remote.myactivity.MyactivityResponse
import com.jubging.jubging.data.remote.myactivity.MyactivityService
import com.jubging.jubging.data.remote.myactivity.MyactivityView
import com.jubging.jubging.databinding.ActivityMyactivityBinding
import com.jubging.jubging.ui.base.BaseActivity

class MyactivityActivity : BaseActivity<ActivityMyactivityBinding>(ActivityMyactivityBinding::inflate),MyactivityView{

    private lateinit var myactivityRVAdapter: MyactivityRVAdapter

    override fun initAfterBinding() {
        binding.myactivityBackIv.setOnClickListener{
            finish()
        }
    }

    private fun initRecyclerView(){
        myactivityRVAdapter = MyactivityRVAdapter(this)
        binding.myactivityRv.adapter = myactivityRVAdapter
    }

    private fun getJubgingList(){
        val myactivityService = MyactivityService()
        myactivityService.setMyactivityView(this)
        myactivityService.getMyactivity()
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
        getJubgingList()
    }

    override fun onMyactivityLoading() {
    }

    override fun onMyactivitySuccess(myactivityResponse: ArrayList<MyactivityResponse>) {
        Log.d("활동성공",myactivityResponse.toString())
        myactivityRVAdapter.addActivity(myactivityResponse)
    }

    override fun onMyactivityFailure(errorCode: Int, message: String) {
        when(errorCode){
            4012 ->{
                Toast.makeText(this, "로그인을 하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4040 ->{
                Toast.makeText(this, "로그인 유저 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }
}