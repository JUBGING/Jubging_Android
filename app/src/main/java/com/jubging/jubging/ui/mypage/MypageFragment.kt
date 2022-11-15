package com.jubging.jubging.ui.mypage

import android.content.Intent
import android.widget.Toast
import com.jubging.jubging.ApplicationClass
import com.jubging.jubging.ApplicationClass.Companion.Authorization
import com.jubging.jubging.data.remote.jubging.UserInfoService
import com.jubging.jubging.databinding.FragmentMypageBinding
import com.jubging.jubging.ui.base.BaseFragment
import com.jubging.jubging.ui.login.LoginActivity
import com.jubging.jubging.ui.main.GetUserInfoView
import com.mummoom.md.data.remote.auth.UserInfoResponse

class MypageFragment:BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate),
    GetUserInfoView {
    override fun initAfterBinding() {
        binding.mypageLogoutTv.setOnClickListener{
            logout()
        }
    }

    override fun onStart() {
        super.onStart()

        getUserResponse()
    }

    private fun getUserResponse(){
        val myUserInfoService = UserInfoService()
        myUserInfoService.setGetUserInfoView(this)
        myUserInfoService.getUserInfo()
    }

    private fun getUserInfo(userInfoResponse: UserInfoResponse){

        binding.mypageNameTv.text = userInfoResponse.name
    }

    private fun logout() {
        val editor = ApplicationClass.mSharedPreferences.edit()
        editor.remove(Authorization)
        editor.apply()

        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
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