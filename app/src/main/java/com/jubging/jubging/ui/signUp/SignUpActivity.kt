package com.jubging.jubging.ui.signUp

import android.view.View
import android.widget.Toast
import com.jubging.jubging.data.entities.User
import com.jubging.jubging.data.remote.auth.AuthService
import com.jubging.jubging.data.remote.auth.SignUpView
import com.jubging.jubging.databinding.ActivitySignUpBinding
import com.jubging.jubging.ui.base.BaseActivity
import java.util.regex.Pattern

class SignUpActivity:BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate),
    SignUpView, View.OnClickListener {

    override fun initAfterBinding() {
        binding.signUpBackIv.setOnClickListener(this)
        binding.signUpSignUpTv.setOnClickListener(this)

    }

    private fun signUp(){
//이메일 형식에 맞게 validation 처리
        var emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        if(binding.signUpEmailEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        else if(!Pattern.matches(emailValidation, binding.signUpEmailEt.text.toString())){
            binding.signUpEmailErrorTv.visibility = View.VISIBLE
            return
        }
        else{
            binding.signUpEmailErrorTv.visibility = View.GONE
        }


        if(binding.signUpNameEt.text.toString().isEmpty()){
            Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

//비밀번호 형식에 맞게 validation 처리
         var pwdValidation = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,14}.$"


        if(binding.signUpPwdEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        else if(!Pattern.matches(pwdValidation,binding.signUpPwdEt.text.toString())){
            binding.signUpPwdErrorTv.visibility = View.VISIBLE
            binding.signUpPwdTv.visibility = View.GONE
            return
        }
        else{
            binding.signUpPwdErrorTv.visibility = View.GONE
            binding.signUpPwdTv.visibility = View.VISIBLE
        }

//비밀번호 확인
        if(binding.signUpPwdCheckEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호 확인을 해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpPwdEt.text.toString() != binding.signUpPwdCheckEt.text.toString()){
            binding.signUpPwdCheckErrorTv.visibility = View.VISIBLE
            return
        }
        else{
            binding.signUpPwdCheckErrorTv.visibility = View.GONE
        }


        AuthService.signUp(this,getUser())
    }

    //유저 정보 불러오고 참조해서
    private fun getUser(): User {
        val email: String = binding.signUpEmailEt.text.toString()
        val password: String = binding.signUpPwdEt.text.toString()
        val name: String = binding.signUpNameEt.text.toString()


        return User(email,name,password)

    }
    override fun onSignUpLoading() {

    }

    override fun onSignUpSuccess() {
        startActivityWithClear(SignUpDoneActivity::class.java)
    }

    override fun onSignUpFailure(errorCode: Int, message: String) {

        when(errorCode){
            //입력 양식 오류 -> 이메일, 비밀번호
            4002 ->{
                Toast.makeText(this, "입력 양식이 틀렸습니다.", Toast.LENGTH_SHORT).show()
                return
            }

            //중복 이메일
            4003 -> {
                binding.signUpEmailDuplicateTv.visibility = View.VISIBLE

            }
            401 -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                return
            }

        }
    }


    override fun onClick(v: View?) {
        if(v == null) return

        when(v){
            binding.signUpBackIv -> finish()
            binding.signUpSignUpTv -> signUp()
        }
    }
}