package com.jubging.jubging.ui.mypage

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.jubging.jubging.ApplicationClass
import com.jubging.jubging.ApplicationClass.Companion.Authorization
import com.jubging.jubging.data.remote.auth.AuthService
import com.jubging.jubging.data.remote.auth.LogoutService
import com.jubging.jubging.data.remote.auth.LogoutView
import com.jubging.jubging.data.remote.userInfo.UserInfoService
import com.jubging.jubging.databinding.FragmentMypageBinding
import com.jubging.jubging.ui.base.BaseFragment
import com.jubging.jubging.data.remote.userInfo.GetUserInfoView
import com.jubging.jubging.ui.jubging.JipgaeNumActivity
import com.jubging.jubging.ui.login.LoginActivity
import com.mummoom.md.data.remote.auth.LogoutResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse

class MypageFragment:BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate),LogoutView,
    GetUserInfoView {
    override fun initAfterBinding() {
        binding.mypageLogoutTv.setOnClickListener{
            logout()


        }
        binding.mypageMyprofileTv.setOnClickListener{
            val intent = Intent(activity, MyprofileActivity::class.java)
            startActivity(intent)
        }
        binding.mypageMyactivityTv.setOnClickListener{
            val intent = Intent(activity, MyactivityActivity::class.java)
            startActivity(intent)
        }
        binding.mypageChangePwdTv.setOnClickListener{
            val intent = Intent(activity, ChangePwdActivity::class.java)
            startActivity(intent)
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

    private fun getLogoutResponse(){
        Log.d("로그아웃 실행","그냥")
        val logoutService = LogoutService()
        logoutService.setLogoutView(this)
        logoutService.logout()
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

    override fun onLogoutLoading() {
    }

    override fun onLogoutSuccess(logoutResponse: LogoutResponse) {
        Log.d("로그아웃성공",logoutResponse.toString())
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }
    override fun onLogoutFailure(errorCode: Int, message: String) {
        when(errorCode){
            4012 ->{
                Toast.makeText(context, "로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show()
                return
            }

        }
    }

}