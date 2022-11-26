package com.jubging.jubging.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jubging.jubging.data.remote.jubging.UserInfoService
import com.jubging.jubging.databinding.FragmentHomeBinding
import com.jubging.jubging.ui.base.BaseFragment
import com.mummoom.md.data.remote.auth.UserInfoResponse

class HomeFragment(): BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),GetUserInfoView {

    override fun initAfterBinding() {


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserResponse()
    }


//    override fun onStart() {
//        super.onStart()
//
//        getUserResponse()
//    }

    private fun getUserResponse(){
        val myUserInfoService = UserInfoService()
        myUserInfoService.setGetUserInfoView(this)
        myUserInfoService.getUserInfo()
    }

    private fun getUserInfo(userInfoResponse: UserInfoResponse){

        binding.homeWalkDataTv.text = userInfoResponse.stepSum.toString()
        binding.homeDistanceDataTv.text = userInfoResponse.distanceSum.toString()
        binding.homeKcalDataTv.text = userInfoResponse.calorieSum.toString()
        binding.homePointDataTv.text = userInfoResponse.totalPoints.toString()
        binding.homeUserNameDataTv.text = userInfoResponse.name

    }

    override fun onGetUserInfoLoading() {

    }

    override fun onGetUserInfoSuccess(userInfoResponse: UserInfoResponse) {
        getUserInfo(userInfoResponse)
    }

    override fun onGetUserInfoFailure(errorCode: Int, message: String) {
        when(errorCode){
            4012 ->{
                Toast.makeText(context, "로그인을 하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4040 ->{
                Toast.makeText(context, "로그인 유저 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }

}