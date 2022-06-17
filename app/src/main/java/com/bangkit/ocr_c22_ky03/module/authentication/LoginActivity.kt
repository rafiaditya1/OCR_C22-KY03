package com.bangkit.ocr_c22_ky03.module.authentication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkit.ocr_c22_ky03.databinding.ActivityLoginBinding
import com.bangkit.ocr_c22_ky03.module.main.MainActivity
import com.bangkit.ocr_c22_ky03.utils.LoginCallbackString
import com.bangkit.ocr_c22_ky03.utils.showLoading

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()
        userPreference = UserPreference(this)

        if (userPreference.preference.getString("email", "") != "") {
            if (userPreference.preference.getString("token", "") != "") {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.login(email, password, object : LoginCallbackString{
                override fun onResponse(msg: String) {
                    Toast.makeText(
                        this@LoginActivity,
                        msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            viewModel.user.observe(this) { user ->
                userPreference.setUserLogin(email, user.accessToken.toString(), user.id)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
    }

    private fun playAnimation() {
        val logoTraveloka = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(1000)
        val logoOcr = ObjectAnimator.ofFloat(binding.logoOcr, View.ALPHA, 1f).setDuration(1000)
        val title = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(1000)
        val subtitle = ObjectAnimator.ofFloat(binding.logToContinue, View.ALPHA, 1f).setDuration(1000)
        val tEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(1000)
        val etEmail = ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(1000)
        val tPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(1000)
        val etPassword = ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(1000)
        val bLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(1000)
        val tDon = ObjectAnimator.ofFloat(binding.tvDontHaveAccount, View.ALPHA, 1f).setDuration(1000)
        val tRegister = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(1000)

        AnimatorSet().apply {
            playTogether(title, subtitle, logoTraveloka, logoOcr, tEmail, etEmail, tPassword, etPassword, bLogin, tDon, tRegister)
            start()
        }
    }
}