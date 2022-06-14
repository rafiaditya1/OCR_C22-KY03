package com.bangkit.ocr_c22_ky03.module.authentication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.ocr_c22_ky03.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -20f, 20f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()

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
//            playSequentially(title, subtitle, logoTraveloka, logoOcr, tEmail, etEmail, tPassword, etPassword, bLogin, tDon, tRegister)
            playTogether(title, subtitle, logoTraveloka, logoOcr, tEmail, etEmail, tPassword, etPassword, bLogin, tDon, tRegister)
            start()
        }
    }
}