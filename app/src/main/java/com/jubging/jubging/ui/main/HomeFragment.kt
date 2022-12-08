package com.jubging.jubging.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jubging.jubging.R
import com.jubging.jubging.data.remote.userInfo.GetUserInfoView
import com.jubging.jubging.data.remote.userInfo.UserInfoService
import com.jubging.jubging.databinding.FragmentHomeBinding
import com.jubging.jubging.ui.base.BaseFragment
import com.mummoom.md.data.remote.auth.UserInfoResponse

class HomeFragment(): BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), GetUserInfoView {

    var jubgingCount = MainActivity.jubgingCountM

    override fun initAfterBinding() {

        if(jubgingCount == 0){
            binding.homeGoal1Iv.setImageResource(R.drawable.earth_black)
            binding.homeGoal2Iv.setImageResource(R.drawable.earth_black)
            binding.homeGoal3Iv.setImageResource(R.drawable.earth_black)
            binding.homeGoal4Iv.setImageResource(R.drawable.earth_black)
        }
        else if(jubgingCount == 1){
            binding.homeGoal1Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal2Iv.setImageResource(R.drawable.earth_black)
            binding.homeGoal3Iv.setImageResource(R.drawable.earth_black)
            binding.homeGoal4Iv.setImageResource(R.drawable.earth_black)
        }
        else if(jubgingCount == 2){
            binding.homeGoal1Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal2Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal3Iv.setImageResource(R.drawable.earth_black)
            binding.homeGoal4Iv.setImageResource(R.drawable.earth_black)
        }
        else if(jubgingCount == 3){
            binding.homeGoal1Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal2Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal3Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal4Iv.setImageResource(R.drawable.earth_black)
        }
        else {
            binding.homeGoal1Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal2Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal3Iv.setImageResource(R.drawable.earth_color)
            binding.homeGoal4Iv.setImageResource(R.drawable.earth_color)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserResponse()
    }

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

        setPlantImage(userInfoResponse.totalPoints)

    }

    private fun setPlantImage(point: Int){
        if (point<100){
            binding.homePlantSmallIv.visibility = View.VISIBLE
            binding.homePlantMediumIv.visibility = View.GONE
            binding.homePlantLargeIv.visibility = View.GONE
            binding.homeBranchSmallIv.visibility = View.GONE
            binding.homeBranchMediumIv.visibility = View.GONE
            binding.homeBranchLargeIv.visibility = View.GONE
            binding.homeTreeSmallIv.visibility = View.GONE
            binding.homeTreeMediumIv.visibility = View.GONE
            binding.homeTreeLargeIv.visibility = View.GONE
        }
        else if (point>100 && point<500){
            binding.homePlantSmallIv.visibility = View.GONE
            binding.homePlantMediumIv.visibility = View.GONE
            binding.homePlantLargeIv.visibility = View.GONE
            binding.homeBranchSmallIv.visibility = View.GONE
            binding.homeBranchMediumIv.visibility = View.VISIBLE
            binding.homeBranchLargeIv.visibility = View.GONE
            binding.homeTreeSmallIv.visibility = View.GONE
            binding.homeTreeMediumIv.visibility = View.GONE
            binding.homeTreeLargeIv.visibility = View.GONE
        }

        else if (point>500){
            binding.homePlantSmallIv.visibility = View.GONE
            binding.homePlantMediumIv.visibility = View.GONE
            binding.homePlantLargeIv.visibility = View.GONE
            binding.homeBranchSmallIv.visibility = View.GONE
            binding.homeBranchMediumIv.visibility = View.GONE
            binding.homeBranchLargeIv.visibility = View.GONE
            binding.homeTreeSmallIv.visibility = View.GONE
            binding.homeTreeMediumIv.visibility = View.VISIBLE
            binding.homeTreeLargeIv.visibility = View.GONE
        }


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