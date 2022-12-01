package com.jubging.jubging.ui.login

import android.view.View
import android.widget.Toast
import com.jubging.jubging.data.entities.User
import com.jubging.jubging.data.remote.auth.AuthService
import com.jubging.jubging.data.remote.auth.LoginView
import com.jubging.jubging.databinding.ActivityLoginBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.main.MainActivity
import com.jubging.jubging.ui.signUp.SignUpActivity
import com.jubging.jubging.utils.saveJwt

import com.mummoom.md.data.remote.auth.AuthResponse
import java.util.regex.Pattern

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView, View.OnClickListener {
    override fun initAfterBinding() {
        binding.loginLoginTv.setOnClickListener(this)
        binding.loginSingUpTv.setOnClickListener(this)
        binding.loginSignUpView.setOnClickListener(this)

    }

    //뭔가 클릭됐을때
    override fun onClick(v: View?) {
        if(v == null) return

        when(v){
            binding.loginSingUpTv -> startNextActivity(SignUpActivity::class.java)
            binding.loginSignUpView -> startNextActivity(SignUpActivity::class.java)
            binding.loginLoginTv -> login()
        }

    }
    //validation 처리와 사용자가 입력한 값 넘겨주기
    private fun login() {
//이메일 validation
        var emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        if(binding.loginEmailEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        else if(!Pattern.matches(emailValidation, binding.loginEmailEt.text.toString())){
            binding.loginEmailErrorTv.visibility = View.VISIBLE
            return
        }
        else{
            binding.loginEmailErrorTv.visibility = View.GONE
        }

//비밀번호 validation
        var pwdValidation = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,14}.$"
        if(binding.loginPwdEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        else if(!Pattern.matches(pwdValidation,binding.loginPwdEt.text.toString())){
            binding.loginPwdErrorTv.visibility = View.VISIBLE
            return
        }
        else{
            binding.loginPwdErrorTv.visibility = View.GONE
        }

        val email = binding.loginEmailEt.text.toString()
        val password = binding.loginPwdEt.text.toString()
        val user = User(email,"",password)

        AuthService.login(this,user)
    }

    //로딩시 -> 로딩화면 만들지?
    override fun onLoginLoading() {

    }

    //로그인 성공시 jwt 토큰 저장 및 다음 액티비티로 넘어가기
    override fun onLoginSuccess(authResponse: AuthResponse) {
        saveJwt(authResponse.accessToken)
        startActivityWithClear(MainActivity::class.java)

    }

    //로그인 실패 시 에러코드 처리
    override fun onLoginFailure(errorCode: Int, message: String){
        when(errorCode){
            4015 ->{
                Toast.makeText(this, "잘못된 이메일입니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4019 ->{
                Toast.makeText(this, "비밀번호를 잘못 입력했습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4011 ->{
                Toast.makeText(this, "비활성화된 사용자 입니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4010 ->{
                Toast.makeText(this, "거부된 사용자 입니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4017 ->{
                Toast.makeText(this, "탈퇴한 사용자 입니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4002 ->{
                binding.loginPwdErrorTv.visibility = View.VISIBLE
            }
        }

    }


}