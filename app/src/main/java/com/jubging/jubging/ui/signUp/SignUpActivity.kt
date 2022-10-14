package com.jubging.jubging.ui.signUp

import android.view.View
import android.widget.Toast
import com.jubging.jubging.databinding.ActivitySignUpBinding
import com.jubging.jubging.ui.base.BaseActivity

class SignUpActivity:BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate),SignUpView, View.OnClickListener {
    override fun initAfterBinding() {
        binding.signUpBackIv.setOnClickListener(this)
        binding.signUpSignUpTv.setOnClickListener(this)

    }

    private fun signUp(){
        if(binding.signUpEmailEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpNicknameEt.text.toString().isEmpty()){
            Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpPwdEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpPwdCheckEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpPwdEt.text.toString() != binding.signUpPwdCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 서로 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //AuthService.signUp(this(signUpView),getUser())
    }

    //유저 정보 불러오고 참조해서
    private fun getUser(){
        val email: String = binding.signUpEmailEt.text.toString()
        val pwd: String = binding.signUpPwdEt.text.toString()
        val nickname: String = binding.signUpNicknameEt.text.toString()

        return //User(정보들)

    }
    override fun onSignUpLoading() {

    }

    override fun onSignUpSuccess() {
        startActivityWithClear(SignUpDoneActivity::class.java)
    }

    override fun onSignUpFailure() {

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v){
            binding.signUpBackIv -> finish()
            binding.signUpSignUpTv -> signUp()
        }
    }
}