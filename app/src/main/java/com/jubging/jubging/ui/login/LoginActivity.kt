package com.jubging.jubging.ui.login

import android.view.View
import android.widget.Toast
import com.jubging.jubging.databinding.ActivityLoginBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.main.MainActivity
import com.jubging.jubging.ui.signUp.SignUpActivity

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginView, View.OnClickListener {
    override fun initAfterBinding() {
        binding.loginLoginTv.setOnClickListener(this)
        binding.loginSingUpTv.setOnClickListener(this)

    }

    //뭔가 클릭됐을때
    override fun onClick(v: View?) {
        if(v == null) return

        when(v){
            binding.loginSingUpTv -> startNextActivity(SignUpActivity::class.java)
            binding.loginLoginTv -> login()
        }

    }
    //validation 처리와 사용자가 입력한 값 넘겨주기
    private fun login() {
        if(binding.loginEmailEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.loginPwdEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email = binding.loginEmailEt.text.toString()
        val pwd = binding.loginPwdEt.text.toString()
        //val user = 서버에 넘겨줄 데이터 넣기
    }

    //로딩시 -> 로딩화면 만들지?
    override fun onLoginLoading() {

    }

    //로그인 성공시 jwt 토큰 저장 및 다음 액티비티로 넘어가기
    override fun onLoginSuccess() {
        //saveJwt(auth.token)
        startActivityWithClear(MainActivity::class.java)

    }

    //로그인 실패 시 에러코드 처리
    override fun onLoginFailure() {


    }


}